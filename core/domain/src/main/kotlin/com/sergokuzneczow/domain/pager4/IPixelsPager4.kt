package com.sergokuzneczow.domain.pager4

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import java.util.TreeMap

public interface IPixelsPager4<T> {

    public companion object {
        public const val UNREACHABLE_PAGE: Int = -1
        public const val FIRST_PAGE: Int = 1
        public const val LAST_PAGE: Int = Int.MAX_VALUE
    }

    //public fun getItems(): SharedFlow<Answer<T?>>

    public fun getPages(): SharedFlow<Answer<T?>>

    public fun start()

    public fun nextPage()

    //public fun startLoadOrReload(executeWhenStart: () -> Unit = {}, executeWhenReload: () -> Unit = {})

    //public fun stopLoad()

    //public fun reloadPages()

    // public fun canceled()

    public enum class StartStrategy {
        INSTANTLY,
        LAZY,
    }

    public enum class LoadStrategy {
        SEQUENTIALLY,
        PARALLEL,
    }

    public enum class PlaceholdersStrategy {
        WITH,
        WITHOUT,
    }

    public enum class RefreshStrategy {
        /**
         * Константа [INSTANTLY] указывает на немедленную синхронизацию локальный и удаленных данных.*/
        INSTANTLY,

        /**
         * Константа [LAZY] указывает на отложенную синхронизацию локальный и удаленных данных, при вызове метода [sync].*/
        LAZY,
    }

    public class Builder<T>(
        private val coroutineScope: CoroutineScope,
        private val sourceDataBlock: suspend (pageNumber: Int, pageSize: Int) -> Flow<List<T>>,
        private val getActualDataBlock: suspend (pageNumber: Int, pageSize: Int) -> List<T>?,
        private val setActualDataBlock: suspend (pageNumber: Int, pageSize: Int, new: List<T>) -> Unit,
    ) {
        public companion object {
            private const val PAGE_SIZE: Int = 24
            private const val NEXT_SIZE: Int = 0
            private const val PREV_SIZE: Int = 0
            private const val UPDATE_DURATION: Long = 5000L
        }

        private var pageSize: Int = PAGE_SIZE
        private var startPage: Int = FIRST_PAGE
        private var nextSize: Int = NEXT_SIZE
        private var prevSize: Int = PREV_SIZE
        private var updateDuration: Long = UPDATE_DURATION
        private var startStrategy: StartStrategy = StartStrategy.LAZY
        private var loadStrategy: LoadStrategy = LoadStrategy.SEQUENTIALLY
        private var placeholdersStrategy: PlaceholdersStrategy = PlaceholdersStrategy.WITH
        private var refreshStrategy: RefreshStrategy = RefreshStrategy.INSTANTLY
        private var getFirstPageNumberBlock: suspend () -> Int = { FIRST_PAGE }
        private var getLastPageNumberBlock: suspend () -> Int = { LAST_PAGE }
        private var pageDownloadStartedCallback: (pageNumber: Int) -> Unit = {}
        private var pageSyncCompletedCallback: (pageNumber: Int, firstPage: Int, lastPage: Int, isEmpty: Boolean) -> Unit = { _, _, _, _ -> }
        private var sourceDataExceptionsCallback: (pageNumber: Int, throwable: Throwable) -> Unit = { _, _ -> }
        private var syncDataExceptionCallback: (pageNumber: Int, throwable: Throwable) -> Unit = { _, _ -> }


        public fun setPageSize(size: Int): Builder<T> {
            pageSize = size
            return this
        }

        public fun setStartPage(pageNumber: Int): Builder<T> {
            startPage = pageNumber
            return this
        }

        public fun setNextSize(size: Int): Builder<T> {
            nextSize = size
            return this
        }

        public fun setPrevSize(size: Int): Builder<T> {
            prevSize = size
            return this
        }

        public fun setUpdateDuration(duration: Long): Builder<T> {
            updateDuration = duration
            return this
        }

        public fun setPlaceholderStrategy(strategy: PlaceholdersStrategy): Builder<T> {
            placeholdersStrategy = strategy
            return this
        }

        public fun setRefreshStrategy(strategy: RefreshStrategy): Builder<T> {
            refreshStrategy = strategy
            return this
        }

        public fun setLoadStrategy(strategy: LoadStrategy): Builder<T> {
            loadStrategy = strategy
            return this
        }

        public fun setStartStrategy(strategy: StartStrategy): Builder<T> {
            startStrategy = strategy
            return this
        }

        public fun setPageDownloadStartedCallback(callback: (pageNumber: Int) -> Unit): Builder<T> {
            pageDownloadStartedCallback = callback
            return this
        }

        public fun setPageSyncCompletedCallback(callback: (pageNumber: Int, firstPage: Int, lastPage: Int, isEmpty: Boolean) -> Unit): Builder<T> {
            pageSyncCompletedCallback = callback
            return this
        }

        public fun setSourceDataExceptionCallback(callback: (pageNumber: Int, throwable: Throwable) -> Unit): Builder<T> {
            sourceDataExceptionsCallback = callback
            return this
        }

        public fun setSyncDataExceptionCallback(callback: (pageNumber: Int, throwable: Throwable) -> Unit): Builder<T> {
            syncDataExceptionCallback = callback
            return this
        }

        public fun setRequestFirstPageNumber(callback: suspend () -> Int): Builder<T> {
            getFirstPageNumberBlock = callback
            return this
        }

        public fun setRequestLastPageNumber(callback: suspend () -> Int): Builder<T> {
            getLastPageNumberBlock = callback
            return this
        }

        public fun build(): IPixelsPager4<T> {
            return PixelsPager4(
                coroutineScope = coroutineScope,
                sourceDataBlock = sourceDataBlock,
                getActualDataBlock = getActualDataBlock,
                setActualDataBlock = setActualDataBlock,
                getFirstPageNumberBlock = getFirstPageNumberBlock,
                getLastPageNumberBlock = getLastPageNumberBlock,
                pageSize = pageSize,
                startPage = startPage,
                updateDuration = updateDuration,
                startStrategy = startStrategy,
                loadStrategy = loadStrategy,
                placeholdersStrategy = placeholdersStrategy,
                refreshStrategy = refreshStrategy,
            )
        }
    }

    public data class Answer<T>(
        val pages: TreeMap<Int, Page<T>>,
        val meta: Meta,
    ) {
        public data class Page<T>(
            val data: List<T>,
            val pageState: PageState
        ) {

            public enum class PageState {
                PLACEHOLDER,
                CACHED,
                UPDATED;
            }
        }

        public data class Meta(
            val firstLoadedPage: Int,
            val lastLoadedPage: Int,
            val firstPage: Int,
            val lastPage: Int,
            val empty: Boolean,
        )
    }
}