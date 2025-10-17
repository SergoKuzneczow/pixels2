package com.sergokuzneczow.database.impl.framework.models

import androidx.room.ColumnInfo
import com.sergokuzneczow.database.impl.framework.entities.SettingsLocalModel.Companion.SETTINGS_THEME_STATE_COLUMN_NAME
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.models.ApplicationSettings.SystemSettings.ThemeState
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

internal data class SystemSettingsLocalModel(
    @ColumnInfo(name = SETTINGS_THEME_STATE_COLUMN_NAME) val themeState: String,
) {

    @Serializable
    internal enum class ThemeState {
        LIGHT,
        DARK,
        SYSTEM,
    }
}

internal fun ApplicationSettings.SystemSettings.toSystemSettingsLocalModel(): SystemSettingsLocalModel {
    return SystemSettingsLocalModel(
        themeState = Json.encodeToString(this.themeState.toSystemSettingsLocalModelThemeState()),
    )
}

internal fun SystemSettingsLocalModel.toPixelsSettingsSystemSettings(): ApplicationSettings.SystemSettings {
    return ApplicationSettings.SystemSettings(
        themeState = Json.decodeFromString<SystemSettingsLocalModel.ThemeState>(this.themeState).toPixelsSettingsSystemSettingsThemeState(),
    )
}

private fun ThemeState.toSystemSettingsLocalModelThemeState(): SystemSettingsLocalModel.ThemeState {
    return when (this) {
        ThemeState.LIGHT -> SystemSettingsLocalModel.ThemeState.LIGHT
        ThemeState.DARK -> SystemSettingsLocalModel.ThemeState.DARK
        ThemeState.SYSTEM -> SystemSettingsLocalModel.ThemeState.SYSTEM
    }
}

private fun SystemSettingsLocalModel.ThemeState.toPixelsSettingsSystemSettingsThemeState(): ThemeState {
    return when (this) {
        SystemSettingsLocalModel.ThemeState.LIGHT -> ThemeState.LIGHT
        SystemSettingsLocalModel.ThemeState.DARK -> ThemeState.DARK
        SystemSettingsLocalModel.ThemeState.SYSTEM -> ThemeState.SYSTEM
    }
}