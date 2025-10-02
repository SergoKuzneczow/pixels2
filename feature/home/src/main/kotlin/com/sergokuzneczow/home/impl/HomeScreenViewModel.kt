package com.sergokuzneczow.home.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.get_first_page_key.GetFirstPageKey
import com.sergokuzneczow.domain.get_home_screen_pager_use_case.GetHomeScreenPager4UseCase
import com.sergokuzneczow.domain.pager4.IPixelsPager4
import com.sergokuzneczow.home.impl.di.DaggerHomeScreenComponent
import com.sergokuzneczow.home.impl.di.HomeScreenComponent
import com.sergokuzneczow.home.impl.di.dependenciesProvider
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.utilities.logger.Level
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
) : ViewModel() {

    @Inject
    lateinit var getHomeScreenPager4UseCase: GetHomeScreenPager4UseCase

    @Inject
    lateinit var getFirstPageKey: GetFirstPageKey

    private val homeScreenComponent: HomeScreenComponent by lazy {
        DaggerHomeScreenComponent.builder()
            .setDep(context.dependenciesProvider.homeFeatureDependenciesProvide())
            .build()
    }

    private val homeListUiState: MutableStateFlow<HomeListUiState> = MutableStateFlow(HomeListUiState.Loading())

    private val progressBarUiState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        homeScreenComponent.inject(this)

        getHomeScreenPager4UseCase.execute(coroutineScope = viewModelScope + Dispatchers.IO)
            .onEach { answer: IPixelsPager4.Answer<PictureWithRelations?> ->
                answer.pages.values.forEachIndexed { index, page ->
                    log(tag = "HomeScreenViewModel", level = Level.DEBUG) { "getHomeScreenPager4UseCase.execute().onEach(); index=$index, page.data=${page.data}" }
                    log(tag = "HomeScreenViewModel", level = Level.DEBUG) { "getHomeScreenPager4UseCase.execute().onEach(); index=$index, page.pageState=${page.pageState}" }
                }
                homeListUiState.emit(HomeListUiState.Success(suggestedQueriesPages = answer.toSuggestedQueriesPages()))
            }.launchIn(viewModelScope)
    }

    fun getHomeListUiState(): StateFlow<HomeListUiState> = homeListUiState.asStateFlow()

    fun getProgressBarUiState(): StateFlow<Boolean> = progressBarUiState.asStateFlow()

    fun nextPage() {
        getHomeScreenPager4UseCase.nextPage()
    }

    fun getPageKey(pageQuery: PageQuery, pageFilter: PageFilter, completed: (pageKey: Long) -> Unit) {
        viewModelScope.launch {
            val pageKey: Long? = getFirstPageKey.execute(pageQuery = pageQuery, pageFilter = pageFilter)
            pageKey?.let { pageKey -> completed.invoke(pageKey) }
        }
    }

    internal class Factory(@NonUiContext private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
                return HomeScreenViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}