package com.sergokuzneczow.splash.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.splash.impl.SplashScreenIntent
import com.sergokuzneczow.splash.impl.SplashScreenUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class SplashScreenViewModel(
    private val settingsRepositoryApi: SettingsRepositoryApi,
) : ViewModel() {

    private var currentUiState: SplashScreenUiState = SplashScreenUiState.Loading

    private val intentListener: MutableSharedFlow<SplashScreenIntent> = MutableSharedFlow()

    val uiState: StateFlow<SplashScreenUiState> = flow {
        runCatching { settingsRepositoryApi.getSettings() }
            .onSuccess { applicationSettings ->
                delay(1_500)
                if (applicationSettings == null) emit(SplashScreenUiState.NotHaveSettings)
                else emit(SplashScreenUiState.HaveSettings)
            }
            .onFailure {
                delay(3_000)
                intentListener.emit(SplashScreenIntent.GetCurrentSettings)
            }

        intentListener.collect { intent ->
            when (intent) {
                SplashScreenIntent.GetCurrentSettings -> {
                    runCatching { settingsRepositoryApi.getSettings() }
                        .onSuccess { applicationSettings ->
                            if (applicationSettings == null) emit(SplashScreenUiState.NotHaveSettings)
                            else emit(SplashScreenUiState.HaveSettings)
                        }.onFailure {
                            delay(3_000)
                            intentListener.emit(SplashScreenIntent.GetCurrentSettings)
                        }
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = currentUiState,
    )
}