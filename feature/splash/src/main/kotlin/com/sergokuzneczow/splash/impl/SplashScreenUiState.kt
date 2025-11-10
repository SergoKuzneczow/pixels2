package com.sergokuzneczow.splash.impl

internal sealed interface SplashScreenUiState {
    data object Loading : SplashScreenUiState
    data object HaveSettings : SplashScreenUiState
    data object NotHaveSettings : SplashScreenUiState
}

internal sealed interface SplashScreenIntent {
    data object GetCurrentSettings : SplashScreenIntent
}