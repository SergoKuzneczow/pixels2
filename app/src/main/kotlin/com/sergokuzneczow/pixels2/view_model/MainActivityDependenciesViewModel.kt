package com.sergokuzneczow.pixels2.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sergokuzneczow.domain.picture_load_and_save_use_case.LoadAndSavePictureUseCase
import com.sergokuzneczow.pixels2.di.applicationComponent
import com.sergokuzneczow.repository.api.NetworkMonitorApi
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import dagger.Lazy
import jakarta.inject.Inject

internal class MainActivityDependenciesViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var networkMonitorApi: Lazy<NetworkMonitorApi>

    @Inject
    lateinit var settingsRepositoryApi: Lazy<SettingsRepositoryApi>

    @Inject
    lateinit var loadAndSavePictureUseCase: Lazy<LoadAndSavePictureUseCase>

    init {
        application.applicationContext.applicationComponent.inject(this)
    }
}