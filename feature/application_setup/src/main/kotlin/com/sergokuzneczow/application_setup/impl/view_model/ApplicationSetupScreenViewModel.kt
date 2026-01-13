package com.sergokuzneczow.application_setup.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenAction
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenIntent
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenIntent.*
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenState
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenState.ShowSelectedTheme
import com.sergokuzneczow.base.MviViewModel
import com.sergokuzneczow.base.StateCollector
import com.sergokuzneczow.base.copyState
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.utilities.DispatchersApi
import kotlinx.coroutines.delay

internal class ApplicationSetupScreenViewModel(
    dispatchersApi: DispatchersApi,
    private val settingsRepositoryApi: SettingsRepositoryApi,
) : MviViewModel<ApplicationSetupScreenState, ApplicationSetupScreenIntent, ApplicationSetupScreenAction>(
    startState = ApplicationSetupScreenState.Loading,
    stateDispatcher = dispatchersApi.default,
) {

    override suspend fun actionStartState(): ApplicationSetupScreenState {
        while (true) {
            runCatching {
                settingsRepositoryApi.setSettings(ApplicationSettings.DEFAULT)
                settingsRepositoryApi.getSettings() ?: throw IllegalStateException("Application settings can't be null.")
            }.onSuccess { applicationSettings ->
                return ShowSelectedTheme(applicationSettings.systemSettings.themeState)
            }.onFailure {
                delay(1_000)
            }
        }
    }

    override suspend fun StateCollector<ApplicationSetupScreenState>.intentListener(intent: ApplicationSetupScreenIntent) {
        when (intent) {
            is ChangeTheme -> copyState<_, ShowSelectedTheme> {
                val newSettings = updateSettings { it.copy(systemSettings = it.systemSettings.copy(themeState = intent.newThemeState)) }
                ShowSelectedTheme(newSettings.systemSettings.themeState)
            }

            is Done -> updateAction { ApplicationSetupScreenAction.Completed }
        }
    }

    private suspend fun updateSettings(settingsMapping: suspend (applicationSettings: ApplicationSettings) -> ApplicationSettings): ApplicationSettings {
        val currentSettings: ApplicationSettings? = settingsRepositoryApi.getSettings()
        if (currentSettings != null) settingsRepositoryApi.setSettings(settingsMapping.invoke(currentSettings))
        else settingsRepositoryApi.setSettings(settingsMapping.invoke(ApplicationSettings.DEFAULT))
        return settingsRepositoryApi.getSettings() ?: throw IllegalStateException("Application settings can't be null.")
    }

    internal class Factory(
        private val dispatchersApi: DispatchersApi,
        private val settingsRepositoryApi: SettingsRepositoryApi,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(ApplicationSetupScreenViewModel::class.java)) ApplicationSetupScreenViewModel(dispatchersApi, settingsRepositoryApi) as T
            else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}