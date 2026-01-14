package com.sergokuzneczow.home.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.domain.get_home_screen_pager4_use_case.GetHomeScreenPager4UseCase
import com.sergokuzneczow.home.impl.view_model.HomeScreenDependenciesViewModel
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.utilities.DispatchersApi
import dagger.Component

@Component(
    dependencies = [HomeFeatureDependencies::class]
)
internal interface HomeFeatureComponent {
    fun inject(d: HomeScreenDependenciesViewModel)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: HomeFeatureDependencies): Builder
        fun build(): HomeFeatureComponent
    }
}

public interface HomeFeatureDependencies {
    public val dispatchersApi: DispatchersApi
    public val getHomeScreenPager4UseCase: GetHomeScreenPager4UseCase
    public val getFirstPageKeyUseCase: GetFirstPageKeyUseCase
    public val imageLoaderApi: ImageLoaderApi

    public interface Contract {
        public fun homeFeatureDependenciesProvide(): HomeFeatureDependencies
    }
}

internal val Context.dependenciesProvider: HomeFeatureDependencies
    get() = when (this) {
        is HomeFeatureDependencies.Contract -> this.homeFeatureDependenciesProvide()
        is Application -> throw IllegalArgumentException("Application must implement HomeScreenDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }