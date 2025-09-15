package com.sergokuzneczow.home.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.domain.get_home_screen_pager_use_case.GetHomeScreenPagerUseCase
import com.sergokuzneczow.repository.api.ImageLoaderApi


public interface HomeScreenDependencies {
    public fun getGetHomeScreenPagerUseCase(): GetHomeScreenPagerUseCase

    public fun getImageLoaderApi(): ImageLoaderApi

    public interface Contract {
        public fun homeScreenDependenciesProvide(): HomeScreenDependencies
    }
}

internal val Context.dependenciesProvider: HomeScreenDependencies.Contract
    get() = when (this) {
        is HomeScreenDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement HomeScreenDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }