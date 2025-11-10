package com.sergokuzneczow.application_setup.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.repository.api.SettingsRepositoryApi

public interface ApplicationSetupFeatureDependencies {

    public val settingsRepositoryApi: SettingsRepositoryApi

    public interface Contract {
        public fun applicationSetupFeatureDependencies(): ApplicationSetupFeatureDependencies
    }
}

internal val Context.dependenciesProvider: ApplicationSetupFeatureDependencies.Contract
    get() = when (this) {
        is ApplicationSetupFeatureDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement ApplicationSetupFeatureDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }