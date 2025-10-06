package com.sergokuzneczow.suitable_pictures.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPager4UseCase
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPagerUseCase

public interface SuitablePicturesFeatureDependencies {
    public val getSuitablePicturesScreenPager4UseCase: GetSuitablePicturesScreenPager4UseCase
    public val getPage: GetPage

    public interface Contract {
        public fun suitablePicturesFeatureDependenciesProvider(): SuitablePicturesFeatureDependencies
    }
}

internal val Context.dependenciesProvider: SuitablePicturesFeatureDependencies.Contract
    get() = when (this) {
        is SuitablePicturesFeatureDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement HomeScreenDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }