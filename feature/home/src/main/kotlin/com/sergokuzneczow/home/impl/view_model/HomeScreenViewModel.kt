package com.sergokuzneczow.home.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.domain.get_home_screen_pager_use_case.GetHomeScreenPager4UseCase
import com.sergokuzneczow.home.impl.HomeListIntent
import com.sergokuzneczow.home.impl.HomeListUiState
import com.sergokuzneczow.home.impl.models.toSuggestedQueriesPages
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class HomeScreenViewModel(
    private val getHomeScreenPager4UseCase: GetHomeScreenPager4UseCase,
    private val getFirstPageKeyUseCase: GetFirstPageKeyUseCase,
) : ViewModel() {

    private var currentUiState: HomeListUiState = HomeListUiState.Loading

    private val currentUiStateMutex: Mutex = Mutex()

    private val intentListener: MutableSharedFlow<HomeListIntent> = MutableSharedFlow()

    internal val uiState: StateFlow<HomeListUiState> = merge(
        flow {
            updateCurrentUiState { HomeListUiState.Success(suggestedQueriesPages = null) }

            getHomeScreenPager4UseCase.execute(coroutineScope = viewModelScope + Dispatchers.IO).collect { answer ->
                updateCurrentUiState { HomeListUiState.Success(suggestedQueriesPages = answer.toSuggestedQueriesPages()) }
            }
        },
        flow {
            intentListener.collect { intent ->
                when (intent) {
                    HomeListIntent.NextPage -> getHomeScreenPager4UseCase.nextPage()
                    is HomeListIntent.SelectQuery -> {
                        val pageKey: Long? = getFirstPageKeyUseCase.execute(intent.pageQuery, intent.pageFilter)
                        pageKey?.let { pageKey -> updateCurrentUiState { HomeListUiState.OpenSelectedQuery(pageKey) } }
                    }
                }
            }
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = currentUiState,
    )

    fun setIntent(intent: HomeListIntent) {
        viewModelScope.launch { intentListener.emit(intent) }
    }

    private suspend fun FlowCollector<HomeListUiState>.updateCurrentUiState(block: () -> HomeListUiState) {
        currentUiStateMutex.withLock {
            currentUiState = block.invoke()
            emit(currentUiState)
        }
    }
}