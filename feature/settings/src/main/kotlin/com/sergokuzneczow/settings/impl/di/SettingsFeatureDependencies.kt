package com.sergokuzneczow.settings.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.repository.api.SettingsRepositoryApi

public interface SettingsFeatureDependencies {

    public val settingsRepositoryApi: SettingsRepositoryApi

    public interface Contract {
        public fun settingsFeatureDependenciesProvide(): SettingsFeatureDependencies
    }
}

internal val Context.dependenciesProvider: SettingsFeatureDependencies.Contract
    get() = when (this) {
        is SettingsFeatureDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement SearchSuitablePicturesDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }