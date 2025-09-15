package com.sergokuzneczow.home.ui

internal interface ProgressBarUiState {
    data object Visible : ProgressBarUiState
    data object Hide : ProgressBarUiState
}