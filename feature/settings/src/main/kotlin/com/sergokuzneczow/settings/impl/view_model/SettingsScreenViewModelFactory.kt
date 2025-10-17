package com.sergokuzneczow.settings.impl.view_model

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.settings.impl.di.SettingsFeatureComponent
import com.sergokuzneczow.settings.impl.di.dependenciesProvider
import jakarta.inject.Inject

internal class SettingsScreenViewModelFactory(
    @NonUiContext context: Context,
) : ViewModelProvider.Factory {

    @Inject
    lateinit var settingsRepositoryApi: SettingsRepositoryApi

    init {
        SettingsFeatureComponent.Instance
            .get(context.dependenciesProvider.settingsFeatureDependenciesProvide())
            .inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsScreenViewModel::class.java)) {
            return SettingsScreenViewModel(
                settingsRepositoryApi = settingsRepositoryApi,
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}