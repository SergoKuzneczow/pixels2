package com.sergokuzneczow.application_setup.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.application_setup.impl.view_model.ApplicationSetupScreenComponentViewModel
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.utilities.DispatchersApi
import dagger.Component

@Component(
    dependencies = [ApplicationSetupFeatureDependencies::class]
)
internal interface ApplicationSetupFeatureComponent {
    fun inject(d: ApplicationSetupScreenComponentViewModel)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: ApplicationSetupFeatureDependencies): Builder
        fun build(): ApplicationSetupFeatureComponent
    }
}

public interface ApplicationSetupFeatureDependencies {
    public val dispatchersApi: DispatchersApi
    public val settingsRepositoryApi: SettingsRepositoryApi

    public interface Contract {
        public fun applicationSetupFeatureDependencies(): ApplicationSetupFeatureDependencies
    }
}

internal val Context.dependencies: ApplicationSetupFeatureDependencies
    get() = when (this) {
        is ApplicationSetupFeatureDependencies.Contract -> this.applicationSetupFeatureDependencies()
        is Application -> throw IllegalArgumentException("Application must implement ApplicationSetupFeatureDependencies.Contract.")
        else -> this.applicationContext.dependencies
    }