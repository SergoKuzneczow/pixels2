package com.sergokuzneczow.home.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.get_first_page_key.GetFirstPageKey
import com.sergokuzneczow.domain.get_home_screen_pager_use_case.GetHomeScreenPagerUseCase
import com.sergokuzneczow.domain.pager.PixelsPager
import com.sergokuzneczow.home.impl.di.DaggerHomeScreenComponent
import com.sergokuzneczow.home.impl.di.HomeScreenComponent
import com.sergokuzneczow.home.impl.di.dependenciesProvider
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.PictureWithRelations
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
    lateinit var getHomeScreenPagerUseCase: GetHomeScreenPagerUseCase

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

        getHomeScreenPagerUseCase.execute(
            coroutineScope = viewModelScope + Dispatchers.IO,
            loading = { /*showProgressBar.invoke(true) */ },
            completed = { lastPage, isEmpty ->
                /*if (lastPage == 1 && isEmpty) viewModelScope.launch { suggestedQueriesUiState.emit(SuggestedQueriesUiState.Empty) }*/
                /*showProgressBar.invoke(false)*/
            },
            error = { throwable -> }
        )
            .onEach { pages: PixelsPager.Pages<PictureWithRelations?> ->
//                pages.entries.forEach { page: MutableMap.MutableEntry<Int, List<PictureWithRelations?>> ->
//                    log(tag = "HomeScreenViewModel") { "getHomeScreenPagerUseCase.executeMap(); map key=${page.key} value=${page.value}" }
//                }
                homeListUiState.emit(HomeListUiState.Success(suggestedQueriesPages = pages.pages.toSuggestedQueriesPages()))
            }.launchIn(viewModelScope)
    }

    fun getHomeListUiState(): StateFlow<HomeListUiState> = homeListUiState.asStateFlow()

    fun getProgressBarUiState(): StateFlow<Boolean> = progressBarUiState.asStateFlow()

    fun nextPage() {
        getHomeScreenPagerUseCase.nextPage()
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