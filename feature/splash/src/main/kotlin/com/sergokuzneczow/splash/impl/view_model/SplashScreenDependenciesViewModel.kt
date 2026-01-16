package com.sergokuzneczow.splash.impl.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.splash.impl.di.DaggerSplashFeatureComponent
import com.sergokuzneczow.splash.impl.di.dependencies
import com.sergokuzneczow.utilities.DispatchersApi
import jakarta.inject.Inject

internal class SplashScreenDependenciesViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var settingsRepositoryApi: SettingsRepositoryApi

    @Inject
    lateinit var dispatchersApi: DispatchersApi

    init {
        DaggerSplashFeatureComponent.builder()
            .setDependencies(application.applicationContext.dependencies)
            .build()
            .inject(this)
    }
}