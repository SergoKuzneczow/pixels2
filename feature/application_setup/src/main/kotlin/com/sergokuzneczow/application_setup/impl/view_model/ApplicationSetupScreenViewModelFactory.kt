package com.sergokuzneczow.application_setup.impl.view_model

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergokuzneczow.application_setup.impl.di.ApplicationSetupFeatureComponent
import com.sergokuzneczow.application_setup.impl.di.dependenciesProvider
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import jakarta.inject.Inject

internal class ApplicationSetupScreenViewModelFactory(@NonUiContext context: Context) : ViewModelProvider.Factory {

    @Inject
    lateinit var settingsRepositoryApi: SettingsRepositoryApi

    init {
        ApplicationSetupFeatureComponent.Instance
            .get(context.dependenciesProvider.applicationSetupFeatureDependencies())
            .inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApplicationSetupScreenViewModel::class.java)) {
            return ApplicationSetupScreenViewModel(
                settingsRepositoryApi = settingsRepositoryApi,
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}