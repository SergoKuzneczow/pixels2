package com.sergokuzneczow.home.impl.view_model

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.domain.get_home_screen_pager_use_case.GetHomeScreenPager4UseCase
import com.sergokuzneczow.home.impl.di.HomeFeatureComponent
import com.sergokuzneczow.home.impl.di.dependenciesProvider
import javax.inject.Inject

internal class HomeScreenViewModelFactory(@NonUiContext private val context: Context) : ViewModelProvider.Factory {

    @Inject
    lateinit var getHomeScreenPager4UseCase: GetHomeScreenPager4UseCase

    @Inject
    lateinit var getFirstPageKeyUseCase: GetFirstPageKeyUseCase

    init {
        HomeFeatureComponent.Instance
            .get(context.dependenciesProvider.homeFeatureDependenciesProvide())
            .inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(
                getHomeScreenPager4UseCase = getHomeScreenPager4UseCase,
                getFirstPageKeyUseCase = getFirstPageKeyUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}