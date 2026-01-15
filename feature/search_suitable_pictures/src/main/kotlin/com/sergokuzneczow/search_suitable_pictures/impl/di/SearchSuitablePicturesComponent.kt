package com.sergokuzneczow.search_suitable_pictures.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.search_suitable_pictures.impl.view_models.SearchSuitablePicturesScreenDependenciesViewModel
import dagger.Component

@Component(
    dependencies = [SearchSuitablePicturesFeatureDependencies::class]
)
internal interface SearchSuitablePicturesComponent {
    fun inject(model: SearchSuitablePicturesScreenDependenciesViewModel)

    @Component.Builder
    interface Builder {
        fun setDep(d: SearchSuitablePicturesFeatureDependencies): Builder
        fun build(): SearchSuitablePicturesComponent
    }
}

public interface SearchSuitablePicturesFeatureDependencies {

    public val pageRepositoryApi: PageRepositoryApi

    public interface Contract {
        public fun searchSuitablePicturesDependenciesProvider(): SearchSuitablePicturesFeatureDependencies
    }
}

internal val Context.dependencies: SearchSuitablePicturesFeatureDependencies
    get() = when (this) {
        is SearchSuitablePicturesFeatureDependencies.Contract -> this.searchSuitablePicturesDependenciesProvider()
        is Application -> throw IllegalArgumentException("Application must implement SearchSuitablePicturesDependencies.Contract.")
        else -> this.applicationContext.dependencies
    }