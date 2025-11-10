package com.sergokuzneczow.repository.impl.settings_repository_impl

import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

public class SettingsRepositoryFakeImpl @Inject constructor(
    public val getSettingsReturn: (() -> ApplicationSettings?)? = null,
    public val getSettingsAsFlowReturn: (() -> MutableStateFlow<ApplicationSettings?>)? = null,
) : SettingsRepositoryApi {

    override suspend fun getSettings(): ApplicationSettings? = getSettingsReturn?.invoke()

    override fun getSettingsAsFlow(): Flow<ApplicationSettings?> {
        return getSettingsAsFlowReturn?.invoke()
            ?: throw IllegalStateException("Property getSettingsAsFlowReturn must be initialize in constructor.")
    }

    override suspend fun setSettings(applicationSettings: ApplicationSettings) {
        getSettingsAsFlowReturn?.invoke()?.emit(applicationSettings)
    }
}