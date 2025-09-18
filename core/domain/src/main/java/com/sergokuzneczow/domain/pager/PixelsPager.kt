package com.sergokuzneczow.domain.pager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import java.util.TreeMap

public interface PixelsPager<T> {

    public companion object {
        public const val UNREACHABLE_PAGE: Int = -1
        public const val FIRST_PAGE: Int = 1
        public const val LAST_PAGE: Int = 999_999
    }

    public fun dataFlow(): SharedFlow<Answer<T?>>

    public fun mapFlow(): SharedFlow<TreeMap<Int, List<T?>>>

    public fun nextPage()

    public fun startLoad()

    public fun startLoadOrReload(executeWhenStart: () -> Unit = {}, executeWhenReload: () -> Unit = {})

    public fun stopLoad()

    public fun reloadPages()

    public fun onOnlineMode()

    public fun onOfflineMode()

    // public fun canceled()

    public enum class StartStrategy {
        START_INSTANTLY,
        START_LAZY,
    }

    public enum class RefreshStrategy {
        /**
         * Константа [REFRESH_INSTANTLY] указывает на немедленную синхронизацию локальный и удаленных данных.*/
        REFRESH_INSTANTLY,

        /**
         * Константа [REFRESH_LAZY] указывает на отложенную синхронизацию локальный и удаленных данных, при вызове метода [sync].*/
        REFRESH_LAZY,
    }

    /**
     * Константа [ReSyncStrategy] указывает, будет ли выполнена повторная попытка синхронизации удаленных и локальных данных при неудачной предыдущей попытке.*/
    public enum class ReSyncStrategy {
        RESYNC,
        NOT_RESYNC,
    }

    public class Builder<T>(
        private val coroutineScope: CoroutineScope,
        private val sourceData: suspend (pageNumber: Int, pageSize: Int) -> Flow<List<T>>,
        private val syncData: suspend (pageNumber: Int, pageSize: Int) -> Unit,
    ) {
        public companion object {
            private const val PAGE_SIZE: Int = 24
            private const val NEXT_SIZE: Int = 0
            private const val PREV_SIZE: Int = 0
            private const val UPDATE_DURATION: Long = 5_000
            private const val WITH_PLACEHOLDERS: Boolean = true
        }

        private var pageSize: Int = PAGE_SIZE
        private var startPage: Int = FIRST_PAGE
        private var nextSize: Int = NEXT_SIZE
        private var prevSize: Int = PREV_SIZE
        private var updateDuration: Long = UPDATE_DURATION
        private var withPlaceholders: Boolean = WITH_PLACEHOLDERS
        private var refreshStrategy: RefreshStrategy = RefreshStrategy.REFRESH_INSTANTLY
        private var reSyncStrategy: ReSyncStrategy = ReSyncStrategy.RESYNC
        private var startStrategy: StartStrategy = StartStrategy.START_LAZY
        private var sourceGetFirstPageNumber: suspend () -> Int = { FIRST_PAGE }
        private var sourceGetLastPageNumber: suspend () -> Int = { LAST_PAGE }
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

        public fun setWithPlaceholder(hasPlaceholders: Boolean): Builder<T> {
            withPlaceholders = hasPlaceholders
            return this
        }

        public fun setRefreshType(strategy: RefreshStrategy): Builder<T> {
            refreshStrategy = strategy
            return this
        }

        public fun setReSyncStrategy(strategy: ReSyncStrategy): Builder<T> {
            reSyncStrategy = strategy
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
            sourceGetFirstPageNumber = callback
            return this
        }

        public fun setRequestLastPageNumber(callback: suspend () -> Int): Builder<T> {
            sourceGetLastPageNumber = callback
            return this
        }

        public fun build(): PixelsPager<T> {
            return PixelsPager3(
                coroutineScope = coroutineScope,
                sourceDataBlock = sourceData,
                syncDataBlock = syncData,
                sourceGetFirstPageNumber = sourceGetFirstPageNumber,
                sourceGetLastPageNumber = sourceGetLastPageNumber,
                pageSize = pageSize,
                startPage = startPage,
                nextSize = nextSize,
                prevSize = prevSize,
                updateDuration = updateDuration,
                withPlaceholders = withPlaceholders,
                refreshStrategy = refreshStrategy,
                reSyncStrategy = reSyncStrategy,
                startStrategy = startStrategy,
                pageDownloadStartedCallback = pageDownloadStartedCallback,
                pageSyncCompletedCallback = pageSyncCompletedCallback,
                sourceDataExceptionsCallback = sourceDataExceptionsCallback,
                syncDataExceptionCallback = syncDataExceptionCallback,
            )
        }
    }

    public data class Answer<T>(
        val items: List<T?>,
        val meta: Meta,
    ) {

        public data class Meta(
            val firstLoadedPage: Int,
            val lastLoadedPage: Int,
            val firstPage: Int,
            val lastPage: Int,
        )
    }
}