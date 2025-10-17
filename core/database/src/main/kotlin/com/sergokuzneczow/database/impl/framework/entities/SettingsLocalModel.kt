package com.sergokuzneczow.database.impl.framework.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sergokuzneczow.database.impl.framework.entities.SettingsLocalModel.Companion.SETTINGS_TABLE_NAME
import com.sergokuzneczow.database.impl.framework.models.SystemSettingsLocalModel
import com.sergokuzneczow.database.impl.framework.models.UserSettingsLocalModel
import com.sergokuzneczow.database.impl.framework.models.toPixelsSettingsSystemSettings
import com.sergokuzneczow.database.impl.framework.models.toPixelsSettingsUserSettings
import com.sergokuzneczow.database.impl.framework.models.toSystemSettingsLocalModel
import com.sergokuzneczow.database.impl.framework.models.toUserSettingsLocalModel
import com.sergokuzneczow.models.ApplicationSettings

@Entity(tableName = SETTINGS_TABLE_NAME)
internal data class SettingsLocalModel(
    @PrimaryKey
    @ColumnInfo(name = SETTINGS_KEY_COLUMN_NAME) val key: Long,
    @Embedded(prefix = SETTINGS_SYSTEM_SETTINGS_PREFIX) val systemSettings: SystemSettingsLocalModel,
    @Embedded(prefix = SETTINGS_USER_SETTINGS_PREFIX) val userSettings: UserSettingsLocalModel,
) {
    companion object {
        const val SETTINGS_TABLE_NAME: String = "pixels_settings"
        const val SETTINGS_KEY_COLUMN_NAME: String = "pixels_settings_key"

        const val SETTINGS_SYSTEM_SETTINGS_PREFIX: String = "system_settings_"
        const val SETTINGS_THEME_STATE_COLUMN_NAME: String = SETTINGS_SYSTEM_SETTINGS_PREFIX + "theme_state"

        const val SETTINGS_USER_SETTINGS_PREFIX: String = "user_settings_"
        const val SETTINGS_PURITY_MODE_COLUMN_NAME: String = SETTINGS_USER_SETTINGS_PREFIX + "purity_mode"
    }
}

internal fun SettingsLocalModel.toPixelsSettings(): ApplicationSettings {
    return ApplicationSettings(
        userSettings = this.userSettings.toPixelsSettingsUserSettings(),
        systemSettings = this.systemSettings.toPixelsSettingsSystemSettings(),
    )
}

internal fun ApplicationSettings.toPixelsSettingsLocalModel(key: Long): SettingsLocalModel {
    return SettingsLocalModel(
        key = key,
        systemSettings = this.systemSettings.toSystemSettingsLocalModel(),
        userSettings = this.userSettings.toUserSettingsLocalModel(),
    )
}