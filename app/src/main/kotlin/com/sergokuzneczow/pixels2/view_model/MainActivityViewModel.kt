package com.sergokuzneczow.pixels2.view_model

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.picture_load_and_save_use_case.LoadAndSavePictureUseCase
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.NetworkMonitorApi
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class MainActivityViewModel(
    networkMonitorApi: NetworkMonitorApi,
    settingsRepositoryApi: SettingsRepositoryApi,
    private val loadAndSavePictureUseCase: LoadAndSavePictureUseCase,
) : ViewModel() {

    internal val themeState: StateFlow<Boolean?> = settingsRepositoryApi.getSettingsAsFlow()
        .map {
            it?.systemSettings?.themeState ?: ApplicationSettings.SystemSettings.ThemeState.SYSTEM
        }.map {
            when (it) {
                ApplicationSettings.SystemSettings.ThemeState.LIGHT -> false
                ApplicationSettings.SystemSettings.ThemeState.DARK -> true
                ApplicationSettings.SystemSettings.ThemeState.SYSTEM -> null
            }
        }.flowOn(
            context = Dispatchers.IO
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = null,
        )

    internal val networkState: StateFlow<Boolean> = networkMonitorApi.networkStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = true,
        )

    private val toastListener: MutableSharedFlow<String> = MutableSharedFlow()

    internal val toastState: StateFlow<String?> = flow {
        toastListener.collect { emit(it) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    private val progressListener: MutableSharedFlow<Boolean> = MutableStateFlow(true)

    internal val progressState: StateFlow<Boolean> = flow {
        progressListener.collect { emit(it) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = true,
    )

    internal fun loadAndSavePicture(picturePath: String, block: (result: Result<Uri>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            loadAndSavePictureUseCase.execute(
                picturePath = picturePath,
                result = { result: Result<Uri> -> block.invoke(result) },
            )
        }
    }

    internal fun setToast(message: String) {
        viewModelScope.launch { toastListener.emit(message) }
    }

    internal fun setProgress(isVisible: Boolean) {
        viewModelScope.launch { progressListener.emit(isVisible) }
    }
}