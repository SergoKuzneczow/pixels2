package com.sergokuzneczow.domain.pager4

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.time.Duration.Companion.seconds


class PixelsPager4Test {

    /**
     * Тест эмулирует поведение IPixelsPager4 в ситуации, когда нет кешированных данных и должны быть получены placeholders, а после актуальные данные.*/
    @Test
    fun `emit first placeholders, after emit data`(): TestResult = runTest {
        val firstPage = 1
        val pageSize = 24
        val cached: List<String> = emptyList()
        val new: List<String> = List(pageSize) { "new $firstPage" }
        val expectedStartData: List<String?> = List(24) { null }
        val expectedNewData: List<String?> = new

        val fakeDataSource: FakeDataSource<String> = FakeDataSource(cached, 0)

        val pager4: IPixelsPager4<String?> = IPixelsPager4.Builder(
            coroutineScope = backgroundScope,
            sourceDataBlock = { pageNumber, _ -> fakeDataSource.scores() },
            getActualDataBlock = { pageNumber, pageSize ->
                delay(1.seconds)
                new
            },
            setActualDataBlock = { pageNumber, pageSize, new -> fakeDataSource.emit(new) }
        ).setStartStrategy(IPixelsPager4.StartStrategy.INSTANTLY)
            .setPlaceholderStrategy(IPixelsPager4.PlaceholdersStrategy.WITH)
            .setRefreshStrategy(IPixelsPager4.RefreshStrategy.INSTANTLY)
            .setStartPage(firstPage)
            .setRequestFirstPageNumber { firstPage }
            .setRequestLastPageNumber { firstPage }
            .setPageSize(pageSize)
            .build()

        pager4.getPages().test {
            val first: IPixelsPager4.Answer.Page<String?>? = awaitItem().pages[firstPage]
            assertEquals(expectedStartData, first?.data)
            assertEquals(IPixelsPager4.Answer.Page.PageState.PLACEHOLDER, first?.pageState)
            val second: IPixelsPager4.Answer.Page<String?>? = awaitItem().pages[firstPage]
            assertEquals(expectedNewData, second?.data)
            assertEquals(IPixelsPager4.Answer.Page.PageState.UPDATED, second?.pageState)
        }
    }

    /**
     * Тест эмулирует поведение IPixelsPager4 в ситуации, когда имеются некоторые кешированные данных размерности отличной от pageSize, которые должны измениться при получениее пустых актуальных данных. */
    @Test
    fun `clear page if has old incorrect data and emit empty actual data`(): TestResult = runTest {
        val firstPage = 1
        val pageSize = 24
        val cached: List<String> = List(pageSize / 2) { "old $firstPage" }
        val updated: List<String> = emptyList()
        val expectedCached: List<String?> = cached
        val expectedUpdated: List<String?> = updated

        val fakeDataSource: FakeDataSource<String> = FakeDataSource(cached, 0)

        val pager4: IPixelsPager4<String?> = IPixelsPager4.Builder(
            coroutineScope = backgroundScope,
            sourceDataBlock = { pageNumber, _ -> fakeDataSource.scores() },
            getActualDataBlock = { pageNumber, pageSize ->
                delay(1_000)
                updated
            },
            setActualDataBlock = { pageNumber, pageSize, new -> fakeDataSource.emit(new) }
        ).setStartStrategy(IPixelsPager4.StartStrategy.INSTANTLY)
            .setPlaceholderStrategy(IPixelsPager4.PlaceholdersStrategy.WITH)
            .setRefreshStrategy(IPixelsPager4.RefreshStrategy.INSTANTLY)
            .setStartPage(firstPage)
            .setRequestFirstPageNumber { firstPage }
            .setRequestLastPageNumber { firstPage }
            .setPageSize(pageSize)
            .build()

        pager4.getPages().test {
            val first: IPixelsPager4.Answer.Page<String?>? = awaitItem().pages[firstPage]
            assertEquals(expectedCached, first?.data)
            assertEquals(IPixelsPager4.Answer.Page.PageState.CACHED, first?.pageState)
            val second: IPixelsPager4.Answer.Page<String?>? = awaitItem().pages[firstPage]
            assertEquals(expectedUpdated, second?.data)
            assertEquals(IPixelsPager4.Answer.Page.PageState.UPDATED, second?.pageState)
        }
    }

    /**
     * Тест эмулирует поведение IPixelsPager4 в ситуации, когда получение актуальных данных из удаленного источника выполняется быстрее, чем получение кешированных данных.*/
    @Test
    fun `must skip cached data`(): TestResult = runTest {
        val firstPage = 1
        val pageSize = 24
        val cached: List<String> = List(12) { "old $firstPage" }
        val new: List<String> = emptyList()
        val expectedNew: List<String?> = new

        val fakeDataSource: FakeDataSource<String> = FakeDataSource(cached, 1_000)

        val pager4: IPixelsPager4<String?> = IPixelsPager4.Builder(
            coroutineScope = backgroundScope,
            sourceDataBlock = { pageNumber, _ -> fakeDataSource.scores() },
            getActualDataBlock = { pageNumber, pageSize -> new },
            setActualDataBlock = { pageNumber, pageSize, new -> fakeDataSource.emit(new) }
        ).setStartStrategy(IPixelsPager4.StartStrategy.INSTANTLY)
            .setPlaceholderStrategy(IPixelsPager4.PlaceholdersStrategy.WITH)
            .setRefreshStrategy(IPixelsPager4.RefreshStrategy.INSTANTLY)
            .setStartPage(firstPage)
            .setRequestFirstPageNumber { firstPage }
            .setRequestLastPageNumber { firstPage }
            .setPageSize(pageSize)
            .build()

        pager4.getPages().test {
            val first: IPixelsPager4.Answer.Page<String?>? = awaitItem().pages[firstPage]
            assertEquals(expectedNew, first?.data)
            assertEquals(IPixelsPager4.Answer.Page.PageState.UPDATED, first?.pageState)
        }
    }
}

private class FakeDataSource<T>(startData: List<T>, private val startDuration: Long) {
    private val flow: MutableStateFlow<List<T?>> = MutableStateFlow(startData)
    suspend fun emit(list: List<T?>) = flow.emit(list)
    fun scores(): Flow<List<T?>> = flow.onStart { delay(startDuration) }
}