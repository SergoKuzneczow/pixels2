package com.sergokuzneczow.splash.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.repository.api.SettingsRepositoryApi

public interface SplashFeatureDependencies {

    public val settingsRepositoryApi: SettingsRepositoryApi

    public interface Contract {
        public fun splashFeatureDependenciesProvide(): SplashFeatureDependencies
    }
}

internal val Context.dependenciesProvider: SplashFeatureDependencies.Contract
    get() = when (this) {
        is SplashFeatureDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement SplashFeatureDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }