package com.sergokuzneczow.splash.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.splash.impl.SplashScreenIntent
import com.sergokuzneczow.splash.impl.SplashScreenUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class SplashScreenViewModel(
    private val settingsRepositoryApi: SettingsRepositoryApi,
) : ViewModel() {

    private var currentUiState: SplashScreenUiState = SplashScreenUiState.Loading

    private var currentUiStateMutex: Mutex = Mutex()

    private val intentListener: MutableSharedFlow<SplashScreenIntent> = MutableSharedFlow()

    val uiState: StateFlow<SplashScreenUiState> = flow {
        val currentSettings: ApplicationSettings? = settingsRepositoryApi.getSettings()

        if (currentSettings == null) {
            settingsRepositoryApi.setSettings(ApplicationSettings.DEFAULT)
            emit(SplashScreenUiState.SelectingThemeState(ApplicationSettings.DEFAULT.systemSettings.themeState))
        } else emit(SplashScreenUiState.Finish)

        intentListener.collect { intent ->
            when (intent) {
                SplashScreenIntent.Skip -> {
                    currentUiStateMutex.withLock {
                        currentUiState = SplashScreenUiState.Finish
                        emit(currentUiState)
                    }
                }

                SplashScreenIntent.Done -> {
                    currentUiStateMutex.withLock {
                        currentUiState = SplashScreenUiState.Finish
                        emit(currentUiState)
                    }
                }

                is SplashScreenIntent.SelectThemeState -> {
                    val currentSettings: ApplicationSettings? = settingsRepositoryApi.getSettings()
                    currentUiStateMutex.withLock {
                        currentSettings?.let {
                            val newSettings: ApplicationSettings = it.copy(systemSettings = it.systemSettings.copy(themeState = intent.themeState))
                            settingsRepositoryApi.setSettings(newSettings)
                        }
                    }
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = currentUiState,
    )

//    internal val uiState: StateFlow<SplashScreenUiState> =
//        merge(dataSourceFlow, intentFlow).flowOn(Dispatchers.IO)
//            .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
//            initialValue = currentUiState,
//        )

    internal fun setIntent(intent: SplashScreenIntent) {
        viewModelScope.launch { intentListener.emit(intent) }
    }
}