package com.sergokuzneczow.repository.impl.settings_repository_impl

import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

public class SettingsRepositoryFakeImpl @Inject constructor(
    public var getSettingsReturn: (() -> ApplicationSettings?)? = null,
    public var getSettingsAsFlowReturn: (() -> MutableStateFlow<ApplicationSettings?>)? = null,
) : SettingsRepositoryApi {

    override suspend fun getSettings(): ApplicationSettings? = getSettingsReturn?.invoke()

    override fun getSettingsAsFlow(): Flow<ApplicationSettings?> {
        return getSettingsAsFlowReturn?.invoke()
            ?: throw IllegalStateException("Property getSettingsAsFlowReturn must be initialize in constructor.")
    }

    override suspend fun setSettings(applicationSettings: ApplicationSettings) {
        getSettingsReturn = { applicationSettings }
        getSettingsAsFlowReturn?.invoke()?.emit(applicationSettings)
    }
}