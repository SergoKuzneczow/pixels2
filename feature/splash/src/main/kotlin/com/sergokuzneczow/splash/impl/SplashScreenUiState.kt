package com.sergokuzneczow.splash.impl

internal sealed interface SplashScreenUiState {
    data object Loading : SplashScreenUiState
    data class Success(val hasSettings: Boolean) : SplashScreenUiState
}