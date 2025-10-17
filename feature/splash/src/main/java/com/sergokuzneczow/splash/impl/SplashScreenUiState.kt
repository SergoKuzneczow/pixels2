package com.sergokuzneczow.splash.impl

import com.sergokuzneczow.models.ApplicationSettings

internal sealed interface SplashScreenUiState {

    data object Loading : SplashScreenUiState

    data object Success : SplashScreenUiState

    data class SelectingThemeState(val themeState: ApplicationSettings.SystemSettings.ThemeState) : SplashScreenUiState
}

internal sealed interface SplashScreenIntent {

    data object Skip : SplashScreenIntent

    data object Done : SplashScreenIntent

    data class SelectThemeState(val themeState: ApplicationSettings.SystemSettings.ThemeState) : SplashScreenIntent
}