package com.sergokuzneczow.splash.impl.view_model

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.splash.impl.di.SplashFeatureComponent
import com.sergokuzneczow.splash.impl.di.dependenciesProvider
import jakarta.inject.Inject

internal class SplashScreenViewModelFactory(@NonUiContext context: Context, ) : ViewModelProvider.Factory {

    @Inject
    lateinit var settingsRepositoryApi: SettingsRepositoryApi

    init {
        SplashFeatureComponent.Instance
            .get(context.dependenciesProvider.splashFeatureDependenciesProvide())
            .inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(
                settingsRepositoryApi = settingsRepositoryApi,
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}