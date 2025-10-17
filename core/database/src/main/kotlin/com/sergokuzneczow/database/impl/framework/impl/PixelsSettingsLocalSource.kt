package com.sergokuzneczow.database.impl.framework.impl

import com.sergokuzneczow.database.impl.framework.RoomHandler
import com.sergokuzneczow.database.impl.framework.dao.PixelsSettingsDao
import com.sergokuzneczow.database.impl.framework.entities.SettingsLocalModel
import com.sergokuzneczow.database.impl.framework.entities.toPixelsSettings
import com.sergokuzneczow.database.impl.framework.entities.toPixelsSettingsLocalModel
import com.sergokuzneczow.models.ApplicationSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PixelsSettingsLocalSource private constructor(val settingsDao: PixelsSettingsDao) {

    internal constructor(roomHandler: RoomHandler) : this(roomHandler.provideSettingsDao())

    private companion object {
        const val DEFAULT_SETTINGS_KEY: Long = 1
    }

    internal suspend fun setSettings(settings: ApplicationSettings) {
        val settingsLocalModel: SettingsLocalModel = settings.toPixelsSettingsLocalModel(DEFAULT_SETTINGS_KEY)
        settingsDao.insertOrReplace(settingsLocalModel)
    }

    internal suspend fun getSettings(): ApplicationSettings? {
        return settingsDao.get(DEFAULT_SETTINGS_KEY)?.toPixelsSettings()
    }

    internal fun getSettingsAsFlow(): Flow<ApplicationSettings?> {
        return settingsDao.getAsFlow(DEFAULT_SETTINGS_KEY).map { it?.toPixelsSettings() }
    }
}