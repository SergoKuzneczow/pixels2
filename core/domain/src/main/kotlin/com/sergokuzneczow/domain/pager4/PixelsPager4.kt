package com.sergokuzneczow.domain.pager4

import com.sergokuzneczow.domain.pager.PixelsPager.Companion.UNREACHABLE_PAGE
import com.sergokuzneczow.domain.pager4.IPixelsPager4.Answer
import com.sergokuzneczow.domain.pager4.IPixelsPager4.LoadStrategy
import com.sergokuzneczow.domain.pager4.IPixelsPager4.PlaceholdersStrategy
import com.sergokuzneczow.domain.pager4.IPixelsPager4.RefreshStrategy
import com.sergokuzneczow.domain.pager4.IPixelsPager4.StartStrategy
import com.sergokuzneczow.utilities.logger.Level
import com.sergokuzneczow.utilities.logger.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.TreeMap
import kotlin.concurrent.Volatile

public class PixelsPager4<T>(
    private val coroutineScope: CoroutineScope,
    private val sourceDataBlock: suspend (pageNumber: Int, pageSize: Int) -> Flow<List<T>>,
    private val getActualDataBlock: suspend (pageNumber: Int, pageSize: Int) -> List<T>?,
    private val setActualDataBlock: suspend (pageNumber: Int, pageSize: Int, new: List<T>) -> Unit,
    private val getFirstPageNumberBlock: suspend () -> Int,
    private val getLastPageNumberBlock: suspend () -> Int,
    private val pageSize: Int,
    private val startPage: Int,
//    private val nextSize: Int,
//    private val prevSize: Int,
    private val updateDuration: Long,
    private val startStrategy: StartStrategy,
    private val loadStrategy: LoadStrategy,
    private val placeholdersStrategy: PlaceholdersStrategy,
    private val refreshStrategy: RefreshStrategy,
//    private val pageDownloadStartedCallback: (pageNumber: Int) -> Unit,
//    private val pageSyncCompletedCallback: (pageNumber: Int, firstPage: Int, lastPage: Int, isEmpty: Boolean) -> Unit,
//    private val sourceDataExceptionsCallback: (pageNumber: Int, throwable: Throwable) -> Unit,
//    private val syncDataExceptionCallback: (pageNumber: Int, throwable: Throwable) -> Unit,
) : IPixelsPager4<T> {

    private val answer: MutableSharedFlow<Answer<T?>> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val pagesMap: TreeMap<Int, Answer.Page<T?>> = TreeMap()

    private val pagesMapMutex: Mutex = Mutex()

    private val syncPageLabels: HashMap<Int, Boolean> = hashMapOf()

    private val syncPageLabelsMutex: Mutex = Mutex()

    private val reloadPageLabels: HashMap<Int, Boolean> = hashMapOf()

    private val sourceDataCoroutines: HashMap<Int, Job> = hashMapOf()

    private val syncDataCoroutines: HashMap<Int, Job> = hashMapOf()

    private var pagerStarted: Boolean = false

    private var waitingLoadedPage: Boolean = false

    private var needNextPage: Boolean = false

    /**
     * Свойтсво [firstPage] хранит значение предела для параметра [prevPage].*/
    @Volatile
    private var firstPage: Int = IPixelsPager4.FIRST_PAGE

    /**
     * Свойство [lastPage] хранит значение предела для параметра [nextPage].*/
    @Volatile
    private var lastPage: Int = IPixelsPager4.LAST_PAGE

    /**
     * Свойство [nextPage] хранит номер следующей страницы.*/
    private var nextPage: Int = IPixelsPager4.UNREACHABLE_PAGE

    /**
     * Свойство [prevPage] хранит номер предыдущей страницы.*/
    private var prevPage: Int = IPixelsPager4.UNREACHABLE_PAGE


    init {
        if (startStrategy == StartStrategy.INSTANTLY) start()
    }

    override fun getPages(): SharedFlow<Answer<T?>> = answer.asSharedFlow()

    override fun start() {
        if (!pagerStarted) {
            pagerStarted = true
            if (nextPage == UNREACHABLE_PAGE) nextPage = startPage + 1
            if (prevPage == UNREACHABLE_PAGE) prevPage = startPage - 1
            downloadPage(startPage)
        }
    }

    override fun nextPage() {
        when (loadStrategy) {
            LoadStrategy.SEQUENTIALLY -> {
                /**
                 * Свойство waitingLoadedPage==false тогда, когда pager не имеет ни одну страницу в состоянии PageState.PLACEHOLDER
                 * (т.е. не пытается получить кешированные или актуальные данные для запрошенной ранее страницы).*/
                if (waitingLoadedPage.not()) {
                    waitingLoadedPage = true
                    coroutineScope.launch {
                        var hasPlaceholders = true
                        while (hasPlaceholders) {
                            pagesMapMutex.withLock { hasPlaceholders = pagesMap.values.firstOrNull { it.pageState == Answer.Page.PageState.PLACEHOLDER } != null }
                            delay(500)
                        }
                        downloadPage(nextPage)
                        nextPage = nextPage + 1
                        waitingLoadedPage = false
                        if (needNextPage) {
                            needNextPage = false
                            nextPage()
                        }
                    }
                } else needNextPage = true
            }

            LoadStrategy.PARALLEL -> {
                downloadPage(nextPage)
                nextPage = nextPage + 1
            }
        }
    }

    private fun downloadPage(pageNumber: Int) {
        if (firstPage <= pageNumber && pageNumber <= lastPage) {
            createSourceDataCoroutine(pageNumber)
            createSyncDataCoroutine(pageNumber)
        }
    }

    private fun createSourceDataCoroutine(pageNumber: Int) {
        sourceDataCoroutines[pageNumber] = coroutineScope.launch(sourceDataExceptionHandler(pageNumber) + Dispatchers.IO) {

            sourceDataBlock(pageNumber, pageSize)
                .onStart { syncPageLabelsMutex.withLock { syncPageLabels[pageNumber] == false } }
                .stateIn(this)
                .onEach {
                    syncPageLabelsMutex.withLock {
                        if (syncPageLabels[pageNumber] == true) {
                            addPage(pageNumber, it, Answer.Page.PageState.UPDATED)
                            // if (it.size!=pageSize && pageNumber!=lastPage) reloadPage() // повторная загрузка при изменении или несовпадении конфигурации
                        } else {
                            if (it.isNotEmpty()) addPage(pageNumber, it, Answer.Page.PageState.CACHED)
                            else if (placeholdersStrategy == PlaceholdersStrategy.WITH) addPlaceholders(pageNumber)
                            else if (placeholdersStrategy == PlaceholdersStrategy.WITHOUT) addPage(pageNumber, emptyList(), Answer.Page.PageState.CACHED)
                        }
                    }
                }.collect()
        }
    }

    private fun createSyncDataCoroutine(pageNumber: Int) {
        syncDataCoroutines[pageNumber] = coroutineScope.launch(syncDataExceptionHandler(pageNumber) + Dispatchers.IO) {
            if (refreshStrategy == RefreshStrategy.INSTANTLY) {
                delayIfReloadingPage(pageNumber)
                runCatching { firstPage = getFirstPageNumberBlock.invoke() }
                runCatching { lastPage = getLastPageNumberBlock.invoke() }
                when {
                    firstPage <= pageNumber && pageNumber <= lastPage -> {
                        val new: List<T>? = getActualDataBlock.invoke(pageNumber, pageSize)
                        new?.let { newNotNull ->
                            syncPageLabelsMutex.withLock {
                                runCatching { setActualDataBlock.invoke(pageNumber, pageSize, newNotNull) }
                                    .onSuccess { syncPageLabels[pageNumber] = true }
                                    .onFailure { throw it }
                            }
                        }
                    }

                    else -> deletePage(pageNumber)
                }
            }
        }
    }

    private fun sourceDataExceptionHandler(pageNumber: Int): CoroutineExceptionHandler = CoroutineExceptionHandler { c, t ->
        log(tag = "Pager4", level = Level.INFO) { "sourceDataExceptionHandler(); pageNumber=$pageNumber; coroutine=$c throwable=$t" }
        //sourceDataExceptionsCallback.invoke(pageNumber, t)
    }

    private fun syncDataExceptionHandler(pageNumber: Int): CoroutineExceptionHandler = CoroutineExceptionHandler { c, t ->
        log(tag = "Pager4", level = Level.INFO) { "syncDataExceptionHandler(); pageNumber=$pageNumber coroutine=$c throwable=$t" }
        //syncDataExceptionCallback.invoke(pageNumber, t)
        reSyncData(pageNumber)
    }

    private suspend fun addPlaceholders(pageNumber: Int) {
        addPage(pageNumber, List(pageSize) { null }, Answer.Page.PageState.PLACEHOLDER)
    }

    private suspend fun addPage(pageNumber: Int, data: List<T?>, pageState: Answer.Page.PageState) {
        pagesMapMutex.withLock {
            pagesMap[pageNumber] = Answer.Page(data, pageState)
            emitChangedPage()
        }
    }

    private suspend fun deletePage(pageNumber: Int) {
        sourceDataCoroutines[pageNumber]?.cancel()
        syncDataCoroutines[pageNumber]?.cancel()
        pagesMap.remove(pageNumber)
        emitChangedPage()
    }

    private suspend fun emitChangedPage() {
        val meta = Answer.Meta(
            firstLoadedPage = pagesMap.keys.first(),
            lastLoadedPage = pagesMap.keys.last(),
            firstPage = firstPage,
            lastPage = lastPage,
            empty = false,
        )
        answer.emit(Answer(pages = pagesMap, meta = meta))
    }

    /**
     * Метод [delayIfReloadingPage] вызывает метод [delay], если это не первая попытка загрузки данных из удаленного источника. */
    private suspend fun delayIfReloadingPage(pageNumber: Int) {
        if (reloadPageLabels.getOrDefault(pageNumber, false)) delay(updateDuration) else reloadPageLabels[pageNumber] = true
    }

    private fun reSyncData(pageNumber: Int) {
        syncDataCoroutines[pageNumber]?.cancel()
        createSyncDataCoroutine(pageNumber)
    }
}