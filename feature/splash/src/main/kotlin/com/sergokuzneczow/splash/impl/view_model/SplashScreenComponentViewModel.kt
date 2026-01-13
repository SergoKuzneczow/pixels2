package com.sergokuzneczow.splash.impl.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.splash.impl.di.DaggerSplashFeatureComponent
import com.sergokuzneczow.splash.impl.di.SplashFeatureComponent
import com.sergokuzneczow.splash.impl.di.dependencies
import com.sergokuzneczow.utilities.DispatchersApi
import jakarta.inject.Inject

internal class SplashScreenComponentViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var settingsRepositoryApi: SettingsRepositoryApi

    @Inject
    lateinit var dispatchersApi: DispatchersApi

    init {
        val component: SplashFeatureComponent = DaggerSplashFeatureComponent.builder()
            .setDependencies(application.applicationContext.dependencies)
            .build()
        component.inject(this)
    }
}