package com.sergokuzneczow.settings.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.settings.impl.view_model.SettingsScreenDependenciesViewModel
import com.sergokuzneczow.utilities.DispatchersApi
import dagger.Component

@Component(
    dependencies = [SettingsFeatureDependencies::class]
)
internal interface SettingsFeatureComponent {
    fun inject(model: SettingsScreenDependenciesViewModel)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: SettingsFeatureDependencies): Builder
        fun build(): SettingsFeatureComponent
    }
}

public interface SettingsFeatureDependencies {
    public val dispatchersApi: DispatchersApi
    public val settingsRepositoryApi: SettingsRepositoryApi

    public interface Contract {
        public fun settingsFeatureDependenciesProvide(): SettingsFeatureDependencies
    }
}

internal val Context.dependencies: SettingsFeatureDependencies
    get() = when (this) {
        is SettingsFeatureDependencies.Contract -> this.settingsFeatureDependenciesProvide()
        is Application -> throw IllegalArgumentException("Application must implement SearchSuitablePicturesDependencies.Contract.")
        else -> this.applicationContext.dependencies
    }