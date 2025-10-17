package com.sergokuzneczow.settings.impl

import com.sergokuzneczow.models.ApplicationSettings

internal sealed interface SettingsScreenUiState {
    var isChanging: Boolean

    data class Loading(override var isChanging: Boolean = false) : SettingsScreenUiState
    data class Success(
        val settings: ApplicationSettings,
        override var isChanging: Boolean = false
    ) : SettingsScreenUiState
}

internal sealed interface SettingsScreenIntent {

    data class ChangeSettingsIntent(val newApplicationSettings: ApplicationSettings) : SettingsScreenIntent
}