package com.sergokuzneczow.suitable_pictures.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPagerUseCase
import com.sergokuzneczow.repository.api.PageRepositoryApi

public interface SuitablePicturesFeatureDependencies {

    public val pageRepositoryApi: PageRepositoryApi

    public val getSuitablePicturesScreenPagerUseCase: GetSuitablePicturesScreenPagerUseCase

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