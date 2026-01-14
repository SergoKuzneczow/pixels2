package com.sergokuzneczow.home.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.domain.get_home_screen_pager4_use_case.GetHomeScreenPager4UseCase
import com.sergokuzneczow.home.impl.HomeScreenIntent
import com.sergokuzneczow.home.impl.HomeScreenSideEffect
import com.sergokuzneczow.home.impl.HomeScreenState
import com.sergokuzneczow.home.impl.models.toSuggestedQueriesPages
import com.sergokuzneczow.utilities.DispatchersApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

internal class HomeScreenViewModel(
    dispatchersApi: DispatchersApi,
    private val getHomeScreenPager4UseCase: GetHomeScreenPager4UseCase,
    private val getFirstPageKeyUseCase: GetFirstPageKeyUseCase,
) : ViewModel(), ContainerHost<HomeScreenState, HomeScreenSideEffect> {

    override val container: Container<HomeScreenState, HomeScreenSideEffect> = viewModelScope.container(HomeScreenState.Loading)

    init {
        getHomeScreenPager4UseCase.execute(coroutineScope = viewModelScope)
            .onEach { answer ->
                if (!answer.meta.empty) dispatch(HomeScreenIntent.UpdatePages(answer.toSuggestedQueriesPages()))
                else dispatch(HomeScreenIntent.UpdatePages(null))
            }
            .launchIn(viewModelScope + dispatchersApi.default)
    }

    fun dispatch(intent: HomeScreenIntent) {
        when (intent) {
            is HomeScreenIntent.NextPage -> intent {
                if (state is HomeScreenState.Success) {
                    reduce { HomeScreenState.Success(suggestedQueriesPages = (state as HomeScreenState.Success).suggestedQueriesPages, isLoadingNextPage = true) }
                    getHomeScreenPager4UseCase.nextPage()
                }
            }

            is HomeScreenIntent.UpdatePages -> intent {
                reduce { HomeScreenState.Success(suggestedQueriesPages = intent.suggestedQueriesPages, isLoadingNextPage = false) }
            }

            is HomeScreenIntent.SelectQuery -> intent {
                runCatching { getFirstPageKeyUseCase.execute(intent.pageQuery, intent.pageFilter) ?: throw IllegalStateException("Page key can't be null.") }
                    .onSuccess { pageKey -> postSideEffect(HomeScreenSideEffect.ShowPages(pageKey)) }
            }
        }
    }

    internal class Factory(
        private val dispatchersApi: DispatchersApi,
        private val getHomeScreenPager4UseCase: GetHomeScreenPager4UseCase,
        private val getFirstPageKeyUseCase: GetFirstPageKeyUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) HomeScreenViewModel(dispatchersApi, getHomeScreenPager4UseCase, getFirstPageKeyUseCase) as T
            else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}