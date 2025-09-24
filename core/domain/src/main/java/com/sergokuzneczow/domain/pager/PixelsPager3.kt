package com.sergokuzneczow.domain.pager

import com.sergokuzneczow.domain.pager.PixelsPager.Companion.UNREACHABLE_PAGE
import com.sergokuzneczow.utilities.logger.Level
import com.sergokuzneczow.utilities.logger.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.TreeMap

public class PixelsPager3<T>(
    private val coroutineScope: CoroutineScope,
    private val sourceDataBlock: suspend (pageNumber: Int, pageSize: Int) -> Flow<List<T>>,
    private val syncDataBlock: suspend (pageNumber: Int, pageSize: Int) -> Unit,
    private val sourceGetFirstPageNumber: suspend () -> Int,
    private val sourceGetLastPageNumber: suspend () -> Int,
    private val pageSize: Int,
    private val startPage: Int,
    private val nextSize: Int,
    private val prevSize: Int,
    private val updateDuration: Long,
    private var withPlaceholders: Boolean,
    private val refreshStrategy: PixelsPager.RefreshStrategy,
    private val reSyncStrategy: PixelsPager.ReSyncStrategy,
    private val startStrategy: PixelsPager.StartStrategy,
    private val pageDownloadStartedCallback: (pageNumber: Int) -> Unit,
    private val pageSyncCompletedCallback: (pageNumber: Int, firstPage: Int, lastPage: Int, isEmpty: Boolean) -> Unit,
    private val sourceDataExceptionsCallback: (pageNumber: Int, throwable: Throwable) -> Unit,
    private val syncDataExceptionCallback: (pageNumber: Int, throwable: Throwable) -> Unit,
) : PixelsPager<T> {

    private val dataFlow: MutableSharedFlow<PixelsPager.Answer<T?>> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val dataMapFlow: MutableSharedFlow<PixelsPager.Pages<T?>> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val pagesMap: TreeMap<Int, List<T?>> = TreeMap()

    private val pagesMapMutex: Mutex = Mutex()

    private val sourceDataCoroutines: HashMap<Int, Job> = hashMapOf()

    private val syncDataCoroutines: HashMap<Int, Job> = hashMapOf()

    private val reloadPageLabels: HashMap<Int, Boolean> = hashMapOf()

    /**
     * Свойтсво [firstPage] хранит значение предела для параметра [prevPage].*/
    private var firstPage: Int = PixelsPager.FIRST_PAGE

    /**
     * Свойство [lastPage] хранит значение предела для параметра [nextPage].*/
    private var lastPage: Int = PixelsPager.LAST_PAGE

    /**
     * Свойство [nextPage] хранит номер следующей страницы.*/
    private var nextPage: Int = UNREACHABLE_PAGE

    /**
     * Свойство [prevPage] хранит номер предыдущей страницы.*/
    private var prevPage: Int = UNREACHABLE_PAGE

    private var pagerStarted: Boolean = false

    init {
        log(tag = "Pager3", level = Level.INFO) { "create instance ${this@PixelsPager3.javaClass}" }
        if (startStrategy == PixelsPager.StartStrategy.START_INSTANTLY && !pagerStarted) {
            downloadStartPages()
            pagerStarted = true
        }
    }

    override fun dataFlow(): SharedFlow<PixelsPager.Answer<T?>> = dataFlow

    override fun mapFlow(): SharedFlow<PixelsPager.Pages<T?>> = dataMapFlow

    override fun nextPage() {
        if (nextPage <= lastPage) {
            downloadPage(nextPage)
            nextPage = nextPage + 1
        }
    }

    override fun startLoad() {
        if (startStrategy == PixelsPager.StartStrategy.START_LAZY && !pagerStarted) {
            pagerStarted = true
            downloadStartPages()
        }
    }

    override fun startLoadOrReload(executeWhenStart: () -> Unit, executeWhenReload: () -> Unit) {
        if (startStrategy == PixelsPager.StartStrategy.START_LAZY && !pagerStarted) {
            executeWhenStart.invoke()
            pagerStarted = true
            downloadStartPages()
        } else {
            executeWhenReload.invoke()
            reloadPages()
        }
    }

    override fun stopLoad() {
        sourceDataCoroutines.forEach { action -> action.value.cancel() }
        syncDataCoroutines.forEach { action -> action.value.cancel() }
        //onCanceled()
    }

    override fun reloadPages() {
        log(tag = "Pager3", level = Level.INFO) { "reloadPages(); enter point" }
        pagesMap.keys.onEach {
            log(tag = "Pager3") { "reloadPages(); reload pageNumber=$it" }
            sourceDataCoroutines[it]?.cancel()
            syncDataCoroutines[it]?.cancel()
            downloadPage(it)
        }
    }

    override fun onOnlineMode() {
        withPlaceholders = false
    }

    override fun onOfflineMode() {
        withPlaceholders = true
    }

    private fun downloadStartPages() {
        log(tag = "Pager3", level = Level.INFO) { "downloadStartPages(); enter point" }
        if (nextPage == UNREACHABLE_PAGE) nextPage = startPage + 1
        if (prevPage == UNREACHABLE_PAGE) prevPage = startPage - 1
        coroutineScope.launch(exceptionStartPageHandler()) {
            createSourceDataCoroutine(startPage)
            createSyncDataCoroutine(startPage).join()
            repeat(nextSize) {
                if (nextPage <= lastPage) {
                    createSourceDataCoroutine(nextPage)
                    createSyncDataCoroutine(nextPage).join()
                    nextPage = nextPage + 1
                }
            }
            repeat(prevSize) {
                if (prevPage >= firstPage) {
                    createSourceDataCoroutine(prevPage)
                    createSyncDataCoroutine(prevPage).join()
                    prevPage = prevPage - 1
                }
            }
        }
    }

    private fun downloadPage(pageNumber: Int) {
        pageDownloadStartedCallback.invoke(pageNumber)
        createSourceDataCoroutine(pageNumber)
        createSyncDataCoroutine(pageNumber)
    }

    private fun createSourceDataCoroutine(pageNumber: Int): Job {
        log(tag = "Pager3", level = Level.INFO) { "createSourceDataCoroutine(); enter point; pageNumber=$pageNumber" }
        val job: Job = coroutineScope.launch(sourceDataExceptionHandler(pageNumber)) {
            if (withPlaceholders) fillPageWithPlaceholders(pageNumber)
            sourceDataBlock(pageNumber, pageSize)
                .stateIn(this)
                .onEach { log(tag = "Pager3") { "sourcePageData(); flow collect value=$it" } }
                .onEach { if (it.isNotEmpty()) fillPageWithData(pageNumber, it) }
                .collect()
        }
        sourceDataCoroutines[pageNumber] = job
        return job
    }

    private fun createSyncDataCoroutine(pageNumber: Int): Job {
        log(tag = "Pager3", level = Level.INFO) { "createSyncDataCoroutine(); enter point; pageNumber=$pageNumber" }
        val job: Job = coroutineScope.launch(syncDataExceptionHandler(pageNumber)) {
            if (refreshStrategy == PixelsPager.RefreshStrategy.REFRESH_INSTANTLY) {
                log(tag = "Pager3") { "createSyncDataCoroutine(); create coroutine for page number $pageNumber" }
                runCatching { /*if (firstPage == UNREACHABLE_PAGE)*/ firstPage = sourceGetFirstPageNumber.invoke() }
                runCatching { /*if (lastPage == UNREACHABLE_PAGE)*/
                    lastPage = sourceGetLastPageNumber.invoke()
                    /*if (lastPage < nextPage)*/ clearOverflow()
                }
                delayIfReloadingPage(pageNumber)
                syncDataBlock(pageNumber, pageSize)
                //clearPlaceholders(pageNumber)
                pageSyncCompletedCallback(pageNumber, firstPage, lastPage, pagesMap.values.flatten().filterNotNull().isEmpty())
//            while (updating) {
//                // фрагмент кода автообновления страницы
//                // нужно сделать либо метод информирования о завершении события или вызывать setOnPageSynchronizationCompleteEvent()
//                delay(updateDuration)
//                syncSourcePageData(pageNumber, pageSize)
//                clearPlaceholders(pageNumber)
//            }
            } else log(tag = "Pager3") { "createSyncDataCoroutine(); can't create coroutine for page number $pageNumber, because refreshStrategy=$refreshStrategy" }
        }
        syncDataCoroutines[pageNumber] = job
        return job
    }

    private fun exceptionStartPageHandler(): CoroutineExceptionHandler = CoroutineExceptionHandler { c, t ->
        log(tag = "Pager3", level = Level.INFO) { "exceptionStartPageHandler(); enter point; coroutine=$c throwable=$t" }
    }

    private fun sourceDataExceptionHandler(pageNumber: Int): CoroutineExceptionHandler = CoroutineExceptionHandler { c, t ->
        log(tag = "Pager3", level = Level.INFO) { "sourceDataExceptionHandler(); enter point; pageNumber=$pageNumber; coroutine=$c throwable=$t" }
        sourceDataExceptionsCallback.invoke(pageNumber, t)
    }

    private fun syncDataExceptionHandler(pageNumber: Int): CoroutineExceptionHandler = CoroutineExceptionHandler { c, t ->
        log(tag = "Pager3", level = Level.INFO) { "syncDataExceptionHandler(); enter point; pageNumber=$pageNumber coroutine=$c throwable=$t" }
        syncDataExceptionCallback.invoke(pageNumber, t)
        if (reSyncStrategy == PixelsPager.ReSyncStrategy.RESYNC) reSyncData(pageNumber)
    }

    private suspend fun fillPageWithPlaceholders(pageNumber: Int) {
        log(tag = "Pager3", level = Level.INFO) { "fillPageWithPlaceholders(); enter point; pageNumber=$pageNumber" }
        val placeholders: List<T?> = List(pageSize) { null }
        fillPageWithData(pageNumber, placeholders)
    }

    private suspend fun fillPageWithData(pageNumber: Int, data: List<T?>) {
        log(tag = "Pager3", level = Level.INFO) { "fillPageWithData(); enter point; pagNumber=$pageNumber; data=$data" }
        pagesMapMutex.withLock { addPage(pageNumber, data) }
    }

//    private suspend fun clearPlaceholders(pageNumber: Int) {
//        log(tag = "Pager3", level = Level.INFO) { "clearPlaceholders(); enter point; pageNumber=$pageNumber" }
//        pagesMapMutex.withLock {
//            pagesMap[pageNumber]?.also { items: List<T?> ->
//                val itemsWithoutNull: List<T> = items.filterNotNull()
//                emitPage(pageNumber, itemsWithoutNull)
//            }
//        }
//    }

    private suspend fun clearOverflow() {
        log(tag = "Pager3", level = Level.INFO) { "clearOverflow();" }
        pagesMapMutex.withLock {
            pagesMap.keys.forEach { key -> if (key > lastPage) deletePage(key) }
        }
    }

    private suspend fun addPage(pageNumber: Int, data: List<T?>) {
        log(tag = "Pager3", level = Level.INFO) { "emitPage(); enter point; pagNumber=$pageNumber; data=$data" }
        pagesMap[pageNumber] = data
        emitPage()
    }

    private suspend fun deletePage(pageNumber: Int) {
        log(tag = "Pager3", level = Level.INFO) { "deletePage();" }
        pagesMap.remove(pageNumber)
        emitPage()
//        val temp: ArrayList<T?> = arrayListOf()
//        temp.ensureCapacity(pagesMap.size * pageSize)
//        pagesMap.values.forEach { temp.addAll(it) }
//
//        val meta = PixelsPager.Meta(
//            firstLoadedPage = pagesMap.keys.first(),
//            lastLoadedPage = pagesMap.keys.last(),
//            firstPage = firstPage,
//            lastPage = lastPage,
//        )
//        val answer: PixelsPager.Answer<T?> = PixelsPager.Answer(
//            items = temp,
//            meta = meta
//        )
//        dataFlow.emit(answer)
//
//        val pages: PixelsPager.Pages<T?> = PixelsPager.Pages(
//            pages = pagesMap,
//            meta = meta,
//        )
//        dataMapFlow.emit(pages)
//        pagesMap.onEach { entry -> log(tag = "Pager3", level = Level.VERBOSE) { "emitPage(); page number ${entry.key} emitted new data: ${entry.value}" } }
    }

    private suspend fun emitPage() {
        val temp: ArrayList<T?> = arrayListOf()
        temp.ensureCapacity(pagesMap.size * pageSize)
        pagesMap.values.forEach { temp.addAll(it) }

        val meta = PixelsPager.Meta(
            firstLoadedPage = pagesMap.keys.first(),
            lastLoadedPage = pagesMap.keys.last(),
            firstPage = firstPage,
            lastPage = lastPage,
        )

        dataFlow.emit(
            PixelsPager.Answer(
                items = temp,
                meta = meta
            )
        )
        dataMapFlow.emit(
            PixelsPager.Pages(
                pages = pagesMap,
                meta = meta,
            )
        )
        pagesMap.onEach { entry -> log(tag = "Pager3", level = Level.VERBOSE) { "emitPage(); page number ${entry.key} emitted new data: ${entry.value}" } }
    }

    private fun reSyncData(pageNumber: Int) {
        syncDataCoroutines[pageNumber]?.cancel()
        pageDownloadStartedCallback.invoke(pageNumber)
        createSyncDataCoroutine(pageNumber)
    }

    /**
     * Метод [delayIfReloadingPage] вызывает метод [delay], если это не первая попытка загрузки данных из удаленного источника. */
    private suspend fun delayIfReloadingPage(pageNumber: Int) {
        if (reloadPageLabels.getOrDefault(pageNumber, false)) delay(updateDuration) else reloadPageLabels[pageNumber] = true
    }
}