package com.sergokuzneczow.splash.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.splash.SplashScreenIntent
import com.sergokuzneczow.splash.impl.SplashScreenUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

internal class SplashScreenViewModel(
    private val settingsRepositoryApi: SettingsRepositoryApi,
) : ViewModel() {

    private var currentUiState: SplashScreenUiState = SplashScreenUiState.Loading

    private val intentListener: MutableSharedFlow<SplashScreenIntent> = MutableSharedFlow()

    val uiState: StateFlow<SplashScreenUiState> = flow {
        delay(1_500)

        var settingsChecked = false
        while (!settingsChecked) {
            runCatching { settingsRepositoryApi.getSettings() }
                .onSuccess { value ->
                    settingsChecked = true
                    if (value == null) emit(SplashScreenUiState.Success(hasSettings = false))
                    else emit(SplashScreenUiState.Success(hasSettings = true))
                }
                .onFailure { delay(1_000) }
        }

        intentListener.collect { intent ->
//            when (intent) {
//                is SplashScreenIntent.CheckApplicationSettings -> {}
//            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 3_000),
        initialValue = currentUiState,
    )
}