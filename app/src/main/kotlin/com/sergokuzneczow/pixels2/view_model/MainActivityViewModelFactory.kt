package com.sergokuzneczow.pixels2.view_model

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergokuzneczow.domain.picture_load_and_save_use_case.LoadAndSavePictureUseCase
import com.sergokuzneczow.pixels2.di.applicationComponent
import com.sergokuzneczow.repository.api.NetworkMonitorApi
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import javax.inject.Inject

internal class MainActivityViewModelFactory(@NonUiContext context: Context) : ViewModelProvider.Factory {

    @Inject
    lateinit var networkMonitorApi: NetworkMonitorApi

    @Inject
    lateinit var settingsRepositoryApi: SettingsRepositoryApi

    @Inject
    lateinit var loadAndSavePictureUseCase: LoadAndSavePictureUseCase

    init {
        context.applicationComponent.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(
                networkMonitorApi = networkMonitorApi,
                settingsRepositoryApi = settingsRepositoryApi,
                loadAndSavePictureUseCase = loadAndSavePictureUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
