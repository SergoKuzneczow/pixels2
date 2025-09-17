package com.sergokuzneczow.home.impl.ui

internal interface ProgressBarUiState {
    data object Visible : ProgressBarUiState
    data object Hide : ProgressBarUiState
}