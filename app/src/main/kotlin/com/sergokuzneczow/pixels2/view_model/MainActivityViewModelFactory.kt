package com.sergokuzneczow.pixels2.view_model

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergokuzneczow.pixels2.SavePictureServiceProvider
import com.sergokuzneczow.pixels2.di.applicationComponent
import com.sergokuzneczow.repository.api.NetworkMonitorApi
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import javax.inject.Inject

internal class MainActivityViewModelFactory(@NonUiContext context: Context) : ViewModelProvider.Factory {

    @Inject
    lateinit var networkMonitorApi: NetworkMonitorApi

    @Inject
    lateinit var savePictureServiceProvider: SavePictureServiceProvider

    @Inject
    lateinit var settingsRepositoryApi: SettingsRepositoryApi

    init {
        context.applicationComponent.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(
                networkMonitorApi = networkMonitorApi,
                savePictureServiceProvider = savePictureServiceProvider,
                settingsRepositoryApi = settingsRepositoryApi,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
