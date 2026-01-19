package com.sergokuzneczow.settings.impl.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.settings.impl.di.DaggerSettingsFeatureComponent
import com.sergokuzneczow.settings.impl.di.dependencies
import com.sergokuzneczow.utilities.DispatchersApi
import jakarta.inject.Inject

internal class SettingsScreenDependenciesViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var dispatchersApi: DispatchersApi

    @Inject
    lateinit var settingsRepositoryApi: SettingsRepositoryApi

    init {
        DaggerSettingsFeatureComponent.builder()
            .setDependencies(application.applicationContext.dependencies)
            .build()
            .inject(this)
    }
}