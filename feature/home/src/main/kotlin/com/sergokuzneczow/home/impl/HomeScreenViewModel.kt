package com.sergokuzneczow.home.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.get_home_screen_pager_use_case.GetHomeScreenPagerUseCase
import com.sergokuzneczow.home.impl.di.DaggerHomeScreenComponent
import com.sergokuzneczow.home.impl.di.HomeScreenComponent
import com.sergokuzneczow.home.impl.di.dependenciesProvider
import com.sergokuzneczow.home.impl.ui.ProgressBarUiState
import com.sergokuzneczow.home.impl.ui.SuggestedQueriesUiState
import com.sergokuzneczow.home.impl.ui.toSuggestedQueries
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.utilities.logger.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

internal class HomeScreenViewModel(
    @NonUiContext context: Context,
    showProgressBar: (Boolean) -> Unit
) : ViewModel() {

    @Inject
    lateinit var getHomeScreenPagerUseCase: GetHomeScreenPagerUseCase

    @Inject
    lateinit var pageRepository: PageRepositoryApi

    private val homeScreenComponent: HomeScreenComponent by lazy {
        DaggerHomeScreenComponent.builder()
            .setDep(context.dependenciesProvider.homeFeatureDependenciesProvide())
            .build()
    }

    private val suggestedQueriesUiState: MutableStateFlow<SuggestedQueriesUiState> = MutableStateFlow(SuggestedQueriesUiState.Default())

    private val progressBarUiState: MutableStateFlow<ProgressBarUiState> = MutableStateFlow(ProgressBarUiState.Visible)

    init {
        homeScreenComponent.inject(this)

        getHomeScreenPagerUseCase.execute(
            coroutineScope = viewModelScope + Dispatchers.IO,
            loading = { showProgressBar.invoke(true) },
            completed = { lastPage, isEmpty ->
                if (lastPage == 1 && isEmpty) viewModelScope.launch { suggestedQueriesUiState.emit(SuggestedQueriesUiState.Empty) }
                showProgressBar.invoke(false)
            },
            error = { throwable -> }
        ).onEach { answer ->
            log(tag = "HomeScreenViewModel") { "getHomeScreenPagerUseCase.execute(); onEach; answer.items=${answer.items}" }
            log(tag = "HomeScreenViewModel") { "getHomeScreenPagerUseCase.execute(); onEach; answer.items.size=${answer.items.size}" }
            log(tag = "HomeScreenViewModel") { "getHomeScreenPagerUseCase.execute(); onEach; answer.meta=${answer.meta}" }
            suggestedQueriesUiState.emit(SuggestedQueriesUiState.Success(answer.items.toSuggestedQueries()))
        }.launchIn(viewModelScope)
    }

    fun getSuggestQueriesUiState(): StateFlow<SuggestedQueriesUiState> = suggestedQueriesUiState.asStateFlow()

    fun getProgressBarUiState(): StateFlow<ProgressBarUiState> = progressBarUiState.asStateFlow()

    fun nextPage() {
        getHomeScreenPagerUseCase.nextPage()
    }

    fun getPageKey(pageQuery: PageQuery, pageFilter: PageFilter, completed: (pageKey: Long) -> Unit) {
        viewModelScope.launch {
            val pageKey: Long? = pageRepository.getPageKey(
                pageQuery = pageQuery,
                pageFilter = pageFilter,
            )
            pageKey?.let { pageKey -> completed.invoke(pageKey) }
        }
    }

    internal class Factory(@NonUiContext private val context: Context, private val showProgressBar: (Boolean) -> Unit) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
                return HomeScreenViewModel(context, showProgressBar) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}