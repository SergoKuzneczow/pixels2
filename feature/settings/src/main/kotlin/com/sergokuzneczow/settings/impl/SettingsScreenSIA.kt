package com.sergokuzneczow.settings.impl

import com.sergokuzneczow.models.ApplicationSettings

internal sealed interface SettingsScreenState {
    data object Loading : SettingsScreenState
    data class Success(
        val themeState: ApplicationSettings.SystemSettings.ThemeState,
        val changingThemeState: Boolean,
    ) : SettingsScreenState
}

internal sealed interface SettingsScreenIntent {
    data class SetSettings(val themeState: ApplicationSettings.SystemSettings.ThemeState) : SettingsScreenIntent
    data class ChangeThemeState(val themeState: ApplicationSettings.SystemSettings.ThemeState) : SettingsScreenIntent
}

internal sealed interface SettingsScreenSideEffect {

}