package com.sergokuzneczow.database.impl.framework.models

import androidx.room.ColumnInfo
import com.sergokuzneczow.database.impl.framework.entities.SettingsLocalModel.Companion.SETTINGS_PURITY_MODE_COLUMN_NAME
import com.sergokuzneczow.models.ApplicationSettings

internal data class UserSettingsLocalModel(
    @ColumnInfo(name = SETTINGS_PURITY_MODE_COLUMN_NAME) val purityMode: Boolean,
)

internal fun ApplicationSettings.UserSettings.toUserSettingsLocalModel(): UserSettingsLocalModel {
    return UserSettingsLocalModel(
        purityMode = this.purityMode,
    )
}

internal fun UserSettingsLocalModel.toPixelsSettingsUserSettings(): ApplicationSettings.UserSettings {
    return ApplicationSettings.UserSettings(
        purityMode = this.purityMode,
    )
}