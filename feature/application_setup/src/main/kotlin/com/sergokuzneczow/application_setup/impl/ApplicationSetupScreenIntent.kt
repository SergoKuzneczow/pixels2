package com.sergokuzneczow.application_setup.impl

import com.sergokuzneczow.models.ApplicationSettings

internal sealed interface ApplicationSetupScreenIntent {
    data class SaveThemeSetting(
        val newThemeState: ApplicationSettings.SystemSettings.ThemeState,
        val completed: () -> Unit,
    ) : ApplicationSetupScreenIntent
    data class SaveDefaultSetting(
        val completed: () -> Unit,
    ) : ApplicationSetupScreenIntent
}