package com.sergokuzneczow.domain.pager4

import app.cash.turbine.test
import com.sergokuzneczow.domain.pager4.IPixelsPager4.Answer.Page.PageState.Cached
import com.sergokuzneczow.domain.pager4.IPixelsPager4.Answer.Page.PageState.Error
import com.sergokuzneczow.domain.pager4.IPixelsPager4.Answer.Page.PageState.Placeholder
import com.sergokuzneczow.domain.pager4.IPixelsPager4.Answer.Page.PageState.Updated
import com.sergokuzneczow.domain.pager4.IPixelsPager4.PlaceholdersStrategy.WITH
import com.sergokuzneczow.domain.pager4.IPixelsPager4.RefreshStrategy.INSTANTLY
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.plus
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
            coroutineScope = backgroundScope + SupervisorJob(),
            sourceDataBlock = { pageNumber, _ -> fakeDataSource.getFlow() },
            getActualDataBlock = { pageNumber, pageSize ->
                delay(1.seconds)
                new
            },
            setActualDataBlock = { pageNumber, pageSize, new -> fakeDataSource.emit(new) }
        ).setStartStrategy(IPixelsPager4.StartStrategy.INSTANTLY)
            .setPlaceholderStrategy(WITH)
            .setRefreshStrategy(INSTANTLY)
            .setStartPage(firstPage)
            .setRequestFirstPageNumber { firstPage }
            .setRequestLastPageNumber { firstPage }
            .setPageSize(pageSize)
            .build()

        pager4.getPages().test {
            val first: IPixelsPager4.Answer.Page<String?>? = awaitItem().pages[firstPage]
            assertEquals(expectedStartData, first?.data)
            assertEquals(Placeholder, first?.pageState)
            val second: IPixelsPager4.Answer.Page<String?>? = awaitItem().pages[firstPage]
            assertEquals(expectedNewData, second?.data)
            assertEquals(Updated, second?.pageState)
        }
    }

    /**
     * Тест эмулирует поведение IPixelsPager4 в ситуации, когда имеются некоторые кешированные данных размерности отличной от pageSize, которые должны измениться при получениее пустых актуальных данных. */
    @Test
    fun `clear page if has old incorrect data and emit empty actual data`(): TestResult = runTest {
        val firstPage = 1
        val pageSize = 24
        val cached: List<String> = List(pageSize / 2) { "old $firstPage" }
        val actual: List<String> = emptyList()
        val expectedCached: List<String?> = cached
        val expectedActual: List<String?> = actual

        val fakeDataSource: FakeDataSource<String> = FakeDataSource(cached, 0)

        val pager4: IPixelsPager4<String?> = IPixelsPager4.Builder(
            coroutineScope = backgroundScope + SupervisorJob(),
            sourceDataBlock = { pageNumber, _ -> fakeDataSource.getFlow() },
            getActualDataBlock = { pageNumber, pageSize ->
                delay(1_000)
                actual
            },
            setActualDataBlock = { pageNumber, pageSize, new -> fakeDataSource.emit(new) }
        ).setStartStrategy(IPixelsPager4.StartStrategy.INSTANTLY)
            .setPlaceholderStrategy(WITH)
            .setRefreshStrategy(INSTANTLY)
            .setStartPage(firstPage)
            .setRequestFirstPageNumber { firstPage }
            .setRequestLastPageNumber { firstPage }
            .setPageSize(pageSize)
            .build()

        pager4.getPages().test {
            val i1: IPixelsPager4.Answer<String?> = awaitItem()
            assertEquals(expectedCached, i1.pages[firstPage]?.data)
            assertEquals(Cached, i1.pages[firstPage]?.pageState)

            val i2: IPixelsPager4.Answer<String?> = awaitItem()
            assertEquals(expectedActual, i2.pages[firstPage]?.data)
            assertEquals(Updated, i2.pages[firstPage]?.pageState)
            assertEquals(true, i2.meta.empty)
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
            coroutineScope = backgroundScope + SupervisorJob(),
            sourceDataBlock = { pageNumber, _ -> fakeDataSource.getFlow() },
            getActualDataBlock = { pageNumber, pageSize -> new },
            setActualDataBlock = { pageNumber, pageSize, new -> fakeDataSource.emit(new) }
        ).setStartStrategy(IPixelsPager4.StartStrategy.INSTANTLY)
            .setPlaceholderStrategy(WITH)
            .setRefreshStrategy(INSTANTLY)
            .setStartPage(firstPage)
            .setRequestFirstPageNumber { firstPage }
            .setRequestLastPageNumber { firstPage }
            .setPageSize(pageSize)
            .build()

        pager4.getPages().test {
            val first: IPixelsPager4.Answer.Page<String?>? = awaitItem().pages[firstPage]
            assertEquals(expectedNew, first?.data)
            assertEquals(Updated, first?.pageState)
        }
    }

    /**
     * Тест эмулирует поведение IPixelsPager4 в ситуации, когда обращение к удаленному ресурсу для синхронизации данных возвращает исключение.*/
    @Test
    fun `catch exception sync process`(): TestResult = runTest {
        val firstPage = 1
        val pageSize = 24
        val exceptionMessage = "Exception"
        val cached: List<String> = List(12) { "old $firstPage" }

        val fakeDataSource: FakeDataSource<String> = FakeDataSource(cached)

        val pager4: IPixelsPager4<String?> = IPixelsPager4.Builder(
            coroutineScope = backgroundScope + SupervisorJob(),
            sourceDataBlock = { pageNumber, _ -> fakeDataSource.getFlow() },
            getActualDataBlock = { pageNumber, pageSize ->
                delay(1.seconds)
                throw IllegalStateException(exceptionMessage)
            },
            setActualDataBlock = { pageNumber, pageSize, new -> fakeDataSource.emit(new) }
        ).setStartStrategy(IPixelsPager4.StartStrategy.INSTANTLY)
            .setPlaceholderStrategy(WITH)
            .setRefreshStrategy(INSTANTLY)
            .setStartPage(firstPage)
            .setRequestFirstPageNumber { firstPage }
            .setRequestLastPageNumber { firstPage }
            .setPageSize(pageSize)
            .build()

        pager4.getPages().test {
            val i1: IPixelsPager4.Answer.Page<String?>? = awaitItem().pages[firstPage]
            assertEquals(cached, i1?.data)
            assertEquals(Cached, i1?.pageState)

            val i2: IPixelsPager4.Answer.Page<String?>? = awaitItem().pages[firstPage]
            assertEquals(cached, i2?.data)
            assertEquals(Error(exceptionMessage), i2?.pageState)
        }
    }

    /**
     * Тест эмулирует поведение IPixelsPager4 в ситуации, когда IPixelsPager4 последовательно загружает страницы, т.е. имеет loadStrategy=SEQUENTIALLY.*/
    @Test
    fun `wait loading next page, when was called nextPage() and loadStrategy=SEQUENTIALLY`(): TestResult = runTest {
        val firstPage = 1
        val secondPage = 2
        val thirdPage = 3
        val fourthPage = 3
        val lastPage = 5
        val pageSize = 4

        val fakeDataSources: HashMap<Int, FakeDataSource<String?>> = hashMapOf(
            Pair(firstPage, FakeDataSource(emptyList())),
            Pair(secondPage, FakeDataSource(emptyList())),
            Pair(thirdPage, FakeDataSource(emptyList())),
            Pair(fourthPage, FakeDataSource(emptyList())),
            Pair(lastPage, FakeDataSource(emptyList())),
        )

        val placeholders: List<String?> = List(pageSize) { null }

        val new: HashMap<Int, List<String>> = hashMapOf(
            Pair(firstPage, List(pageSize) { "new 1" }),
            Pair(secondPage, List(pageSize) { "new 2" }),
            Pair(thirdPage, List(pageSize) { "new 3" }),
            Pair(fourthPage, List(pageSize) { "new 4" }),
            Pair(lastPage, List(pageSize) { "new 5" }),
        )

        val pager4: IPixelsPager4<String?> = IPixelsPager4.Builder(
            coroutineScope = backgroundScope + SupervisorJob(),
            sourceDataBlock = { pageNumber, _ ->
                fakeDataSources[pageNumber]!!.getFlow()
            },
            getActualDataBlock = { pageNumber, pageSize ->
                delay(100)
                new[pageNumber]!!
            },
            setActualDataBlock = { pageNumber, pageSize, new ->
                fakeDataSources[pageNumber]!!.emit(new)
            }
        ).setStartStrategy(IPixelsPager4.StartStrategy.INSTANTLY)
            .setPlaceholderStrategy(WITH)
            .setRefreshStrategy(INSTANTLY)
            .setLoadStrategy(IPixelsPager4.LoadStrategy.SEQUENTIALLY)
            .setStartPage(firstPage)
            .setRequestFirstPageNumber { firstPage }
            .setRequestLastPageNumber { lastPage }
            .setPageSize(pageSize)
            .build()

        pager4.getPages().test(2.seconds) {
            val i1: IPixelsPager4.Answer<String?> = awaitItem()
            assertEquals(
                IPixelsPager4.Answer.Page(placeholders, Placeholder),
                i1.pages.values.toList().get(0)
            )

            val i2 = awaitItem()
            assertEquals(
                IPixelsPager4.Answer.Page(new[firstPage]!!, Updated),
                i2.pages.values.toList().get(0)
            )

            pager4.nextPage()
            pager4.nextPage() // make PixelsPager4.needNextPage=true
            pager4.nextPage() // must ignore

            val i3: IPixelsPager4.Answer<String?> = awaitItem()
            assertEquals(
                IPixelsPager4.Answer.Page(new[firstPage]!!, Updated),
                i3.pages.values.toList().get(0)
            )
            assertEquals(
                IPixelsPager4.Answer.Page(placeholders, Placeholder),
                i3.pages.values.toList().get(1)
            )

            val i4: IPixelsPager4.Answer<String?> = awaitItem()
            assertEquals(
                IPixelsPager4.Answer.Page(new[firstPage]!!, Updated),
                i4.pages.values.toList().get(0)
            )
            assertEquals(
                IPixelsPager4.Answer.Page(new[secondPage]!!, Updated),
                i4.pages.values.toList().get(1)
            )

            val i5: IPixelsPager4.Answer<String?> = awaitItem()
            assertEquals(
                IPixelsPager4.Answer.Page(new[firstPage]!!, Updated),
                i5.pages.values.toList().get(0)
            )
            assertEquals(
                IPixelsPager4.Answer.Page(new[secondPage]!!, Updated),
                i5.pages.values.toList().get(1)
            )
            assertEquals(
                IPixelsPager4.Answer.Page(placeholders, Placeholder),
                i5.pages.values.toList().get(2)
            )

            val i6: IPixelsPager4.Answer<String?> = awaitItem()
            assertEquals(
                IPixelsPager4.Answer.Page(new[firstPage]!!, Updated),
                i6.pages.values.toList().get(0)
            )
            assertEquals(
                IPixelsPager4.Answer.Page(new[secondPage]!!, Updated),
                i6.pages.values.toList().get(1)
            )
            assertEquals(
                IPixelsPager4.Answer.Page(new[thirdPage]!!, Updated),
                i6.pages.values.toList().get(2)
            )
            //awaitItem() // Must down!!!
        }
    }
}

private class FakeDataSource<T>(startData: List<T>, private val startDuration: Long = 0) {
    private val flow: MutableStateFlow<List<T?>> = MutableStateFlow(startData)
    suspend fun emit(list: List<T?>) = flow.emit(list)
    fun getFlow(): Flow<List<T?>> = flow.onStart { delay(startDuration) }
}