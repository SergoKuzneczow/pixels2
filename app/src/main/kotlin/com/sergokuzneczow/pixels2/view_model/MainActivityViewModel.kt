package com.sergokuzneczow.pixels2.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.NetworkMonitorApi
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.service_save_picture.api.PictureSavingServiceProviderApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class MainActivityViewModel(
    private val networkMonitorApi: NetworkMonitorApi,
    private val savePictureServiceProvider: PictureSavingServiceProviderApi,
    settingsRepositoryApi: SettingsRepositoryApi,
) : ViewModel() {

    internal val themeState: StateFlow<ApplicationSettings.SystemSettings.ThemeState> = settingsRepositoryApi.getSettingsAsFlow()
        .map {
            it?.systemSettings?.themeState ?: ApplicationSettings.SystemSettings.ThemeState.SYSTEM
        }.flowOn(
            context = Dispatchers.IO
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = ApplicationSettings.SystemSettings.ThemeState.SYSTEM,
        )

    internal fun getNetworkState(): Flow<Boolean> = networkMonitorApi.networkStateFlow()

    internal fun getSavePictureServiceProvider(): PictureSavingServiceProviderApi = savePictureServiceProvider
}