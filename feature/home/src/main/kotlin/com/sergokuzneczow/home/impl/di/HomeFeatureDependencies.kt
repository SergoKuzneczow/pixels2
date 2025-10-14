package com.sergokuzneczow.home.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.domain.get_home_screen_pager_use_case.GetHomeScreenPager4UseCase
import com.sergokuzneczow.repository.api.ImageLoaderApi


public interface HomeFeatureDependencies {
    public val getHomeScreenPager4UseCase: GetHomeScreenPager4UseCase
    public val getFirstPageKeyUseCase: GetFirstPageKeyUseCase
    public val imageLoaderApi: ImageLoaderApi

    public interface Contract {
        public fun homeFeatureDependenciesProvide(): HomeFeatureDependencies
    }
}

internal val Context.dependenciesProvider: HomeFeatureDependencies.Contract
    get() = when (this) {
        is HomeFeatureDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement HomeScreenDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }