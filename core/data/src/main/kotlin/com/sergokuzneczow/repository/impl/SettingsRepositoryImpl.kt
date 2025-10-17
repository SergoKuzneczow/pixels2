package com.sergokuzneczow.repository.impl

import com.sergokuzneczow.database.api.PixelsDatabaseDataSourceApi
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

public class SettingsRepositoryImpl @Inject constructor(
    private val databaseApi: PixelsDatabaseDataSourceApi,
) : SettingsRepositoryApi {

    override suspend fun getSettings(): ApplicationSettings? = databaseApi.getSettings()

    override fun getSettingsAsFlow(): Flow<ApplicationSettings?> = databaseApi.getSettingsAsFlow()

    override suspend fun setSettings(applicationSettings: ApplicationSettings) {
        databaseApi.setSettings(applicationSettings)
    }
}