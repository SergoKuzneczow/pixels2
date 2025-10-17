package com.sergokuzneczow.repository.api

import com.sergokuzneczow.models.ApplicationSettings
import kotlinx.coroutines.flow.Flow

public interface SettingsRepositoryApi {

    public suspend fun getSettings(): ApplicationSettings?

    public fun getSettingsAsFlow(): Flow<ApplicationSettings?>

    public suspend fun setSettings(applicationSettings: ApplicationSettings)
}