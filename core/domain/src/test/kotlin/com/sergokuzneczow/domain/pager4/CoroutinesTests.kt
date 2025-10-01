package com.sergokuzneczow.domain.pager4

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

//@ExperimentalCoroutinesApi
//@RunWith(MockitoJUnitRunner::class)
//@RunWith(BlockJUnit4ClassRunner::class)
class CoroutinesTests {

    class FakeDataSource<T>(
        startValue: List<T>,
    ) {
        private val _state: MutableStateFlow<List<T>> = MutableStateFlow(startValue)
        val flow: StateFlow<List<T>> = _state.asStateFlow()
        suspend fun emit(list: List<T>) {
            _state.emit(list)
        }
    }

    val fakeDataSource: FakeDataSource<String> = FakeDataSource(List(24) { "start" })

    var fakeCoroutineDispatcherProvider: CoroutineDispatcherProvider? = null

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        fakeCoroutineDispatcherProvider = object : CoroutineDispatcherProvider() {
            override val main: CoroutineDispatcher = StandardTestDispatcher()
            override val default: CoroutineDispatcher = StandardTestDispatcher()
            override val io: CoroutineDispatcher = StandardTestDispatcher()
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
    @Test
    fun test() {
        runTest {
            val pagerMock: PagerMock<String> = PagerMock(
                coroutineScope = backgroundScope,
                coroutineDispatcherProvider = fakeCoroutineDispatcherProvider!!,
                sourceBlock = { fakeDataSource.flow },
                getActualBlock = { List(24) { "actual" } },
                setActualBlock = { fakeDataSource.emit(it) }
            )
            pagerMock.flow.test {
                fakeDataSource.emit(List(24) { "start" })
                val first: List<String> = awaitItem()
                assertEquals(first, List(24) { "start" })

                fakeDataSource.emit(List(24) { "actual" })
                val second: List<String> = awaitItem()
                assertEquals(second, List(24) { "actual" })

                cancelAndIgnoreRemainingEvents()
            }

//            val fakeDataSource: FakeDataSource<String> = FakeDataSource(startValue = listOf("start"))
//            fakeDataSource.flow.test {
//                fakeDataSource.emit(listOf("1"))
//                assertEquals(listOf("1"), awaitItem())
//                fakeDataSource.emit(listOf("2"))
//                assertEquals(listOf("2"), awaitItem())
//            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}

private class PagerMock<T>(
    val coroutineScope: CoroutineScope,
    val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    val sourceBlock: () -> Flow<List<T>>,
    val getActualBlock: suspend () -> List<T>,
    val setActualBlock: suspend (new: List<T>) -> Unit,
) {

    private val data: MutableList<T> = mutableListOf()

    private val _state: MutableSharedFlow<List<T>> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    public val flow: Flow<List<T>> = _state

    init {
        initSource()
        initSync()
    }

    private fun initSource() {
        coroutineScope.launch(coroutineDispatcherProvider.default) {
            sourceBlock.invoke().collect { _state.emit(it) }
        }
    }

    private fun initSync() {
        coroutineScope.launch(coroutineDispatcherProvider.default) {
            val new: List<T> = getActualBlock.invoke()
            setActualBlock.invoke(new)
        }
    }
}

open class CoroutineDispatcherProvider {
    open val main: CoroutineDispatcher = Dispatchers.Main
    open val default: CoroutineDispatcher = Dispatchers.Default
    open val io: CoroutineDispatcher = Dispatchers.IO
}