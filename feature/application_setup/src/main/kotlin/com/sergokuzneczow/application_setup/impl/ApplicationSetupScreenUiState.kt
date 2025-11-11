package com.sergokuzneczow.application_setup.impl

import com.sergokuzneczow.models.ApplicationSettings

internal sealed interface ApplicationSetupScreenUiState {
    data object Loading : ApplicationSetupScreenUiState
    data class SelectingTheme(val themeState: ApplicationSettings.SystemSettings.ThemeState) : ApplicationSetupScreenUiState
}