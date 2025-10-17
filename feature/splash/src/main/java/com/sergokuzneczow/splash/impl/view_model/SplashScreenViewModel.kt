package com.sergokuzneczow.splash.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.splash.impl.SplashScreenIntent
import com.sergokuzneczow.splash.impl.SplashScreenUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class SplashScreenViewModel(
    private val settingsRepositoryApi: SettingsRepositoryApi,
) : ViewModel() {

    private var currentUiState: SplashScreenUiState = SplashScreenUiState.Loading

    private var currentUiStateMutex: Mutex = Mutex()

    private val dataSourceFlow: Flow<SplashScreenUiState> = flow {
        val currentSettings: ApplicationSettings? = settingsRepositoryApi.getSettings()

        if (currentSettings != null) {
            emit(SplashScreenUiState.Success)
        } else {
            settingsRepositoryApi.setSettings(ApplicationSettings.DEFAULT)
            emit(SplashScreenUiState.SelectingThemeState(ApplicationSettings.DEFAULT.systemSettings.themeState))
        }
    }

    private val intentListener: MutableSharedFlow<SplashScreenIntent> = MutableSharedFlow()

    private val intentFlow: Flow<SplashScreenUiState> = flow {
        intentListener.collect { intent ->
            when (intent) {
                SplashScreenIntent.Skip -> {
                    currentUiStateMutex.withLock {
                        currentUiState = SplashScreenUiState.Success
                        emit(currentUiState)
                    }
                }

                SplashScreenIntent.Done -> {
                    currentUiStateMutex.withLock {
                        currentUiState = SplashScreenUiState.Success
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
    }

    internal val uiState: StateFlow<SplashScreenUiState> =
        merge(dataSourceFlow, intentFlow).flowOn(Dispatchers.IO).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = currentUiState,
        )

    internal fun setIntent(intent: SplashScreenIntent) {
        viewModelScope.launch { intentListener.emit(intent) }
    }
}