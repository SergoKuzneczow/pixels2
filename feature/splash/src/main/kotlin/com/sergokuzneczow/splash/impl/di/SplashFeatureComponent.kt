package com.sergokuzneczow.splash.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.splash.impl.view_model.SplashScreenDependenciesViewModel
import com.sergokuzneczow.utilities.DispatchersApi
import dagger.Component

@Component(
    dependencies = [SplashFeatureDependencies::class]
)
internal interface SplashFeatureComponent {

    fun inject(destination: SplashScreenDependenciesViewModel)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: SplashFeatureDependencies): Builder
        fun build(): SplashFeatureComponent
    }
}

public interface SplashFeatureDependencies {
    public val dispatchersApi: DispatchersApi
    public val settingsRepositoryApi: SettingsRepositoryApi
    public interface Contract {
        public fun splashFeatureDependenciesProvide(): SplashFeatureDependencies
    }
}

internal val Context.dependencies: SplashFeatureDependencies
    get() = when (this) {
        is SplashFeatureDependencies.Contract -> this.splashFeatureDependenciesProvide()
        is Application -> throw IllegalArgumentException("Application must implement SplashFeatureDependencies.Contract.")
        else -> this.applicationContext.dependencies
    }