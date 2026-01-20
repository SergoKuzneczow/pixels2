package com.sergokuzneczow.suitable_pictures.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPager4UseCase
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.suitable_pictures.impl.view_model.SuitablePicturesScreenDependenciesViewModel
import com.sergokuzneczow.utilities.DispatchersApi
import dagger.Component

@Component(
    dependencies = [SuitablePicturesFeatureDependencies::class]
)
internal interface SuitablePicturesFeatureComponent {
    fun inject(model: SuitablePicturesScreenDependenciesViewModel)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: SuitablePicturesFeatureDependencies): Builder
        fun build(): SuitablePicturesFeatureComponent
    }
}

public interface SuitablePicturesFeatureDependencies {
    public val getSuitablePicturesScreenPager4UseCase: GetSuitablePicturesScreenPager4UseCase
    public val getPage: GetPage
    public val dispatchersApi: DispatchersApi
    public val imageLoaderApi: ImageLoaderApi

    public interface Contract {
        public fun suitablePicturesFeatureDependenciesProvider(): SuitablePicturesFeatureDependencies
    }
}

internal val Context.dependencies: SuitablePicturesFeatureDependencies
    get() = when (this) {
        is SuitablePicturesFeatureDependencies.Contract -> this.suitablePicturesFeatureDependenciesProvider()
        is Application -> throw IllegalArgumentException("Application must implement HomeScreenDependencies.Contract.")
        else -> this.applicationContext.dependencies
    }