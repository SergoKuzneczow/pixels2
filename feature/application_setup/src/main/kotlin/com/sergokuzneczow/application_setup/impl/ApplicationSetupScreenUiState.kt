package com.sergokuzneczow.application_setup.impl

import com.sergokuzneczow.models.ApplicationSettings

internal sealed interface ApplicationSetupScreenUiState {
    data object Loading : ApplicationSetupScreenUiState
    data object ApplicationSetupCompleted : ApplicationSetupScreenUiState
    data class SelectingTheme(val themeState: ApplicationSettings.SystemSettings.ThemeState) : ApplicationSetupScreenUiState
}

internal sealed interface ApplicationSetupScreenIntent {
    data object Skip : ApplicationSetupScreenIntent
    data object Done : ApplicationSetupScreenIntent
    data object SaveDefaultSettings : ApplicationSetupScreenIntent
    data object GetCurrentThemeState : ApplicationSetupScreenIntent
    data class ThemeSelected(val newThemeState: ApplicationSettings.SystemSettings.ThemeState) : ApplicationSetupScreenIntent
}