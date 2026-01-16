package com.sergokuzneczow.application_setup.impl.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sergokuzneczow.application_setup.impl.di.DaggerApplicationSetupFeatureComponent
import com.sergokuzneczow.application_setup.impl.di.dependencies
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.utilities.DispatchersApi
import jakarta.inject.Inject

internal class ApplicationSetupScreenDependenciesViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var settingsRepositoryApi: SettingsRepositoryApi

    @Inject
    lateinit var dispatchersApi: DispatchersApi

    init {
        DaggerApplicationSetupFeatureComponent.builder()
            .setDependencies(application.applicationContext.dependencies)
            .build()
            .inject(this)
    }
}