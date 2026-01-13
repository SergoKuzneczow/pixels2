package com.sergokuzneczow.splash.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergokuzneczow.base.MviViewModel
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.splash.impl.SplashScreenAction
import com.sergokuzneczow.splash.impl.SplashScreenIntent
import com.sergokuzneczow.splash.impl.SplashScreenState
import com.sergokuzneczow.utilities.DispatchersApi
import kotlinx.coroutines.delay

internal class SplashScreenViewModel(
    dispatchersApi: DispatchersApi,
    private val settingsRepositoryApi: SettingsRepositoryApi,
) : MviViewModel<SplashScreenState, SplashScreenIntent, SplashScreenAction>(
    startState = SplashScreenState.CheckingFirstLaunch,
    stateDispatcher = dispatchersApi.default,
) {

    override suspend fun actionStartState(): SplashScreenState? {
        var settingsChecked = false
        while (!settingsChecked) {
            runCatching { settingsRepositoryApi.getSettings() }
                .onSuccess { settings: ApplicationSettings? ->
                    settingsChecked = true
                    if (settings == null) updateAction { SplashScreenAction.IsFirstLaunch }
                    else updateAction { SplashScreenAction.IsNotFirstLaunch(settings) }
                }
                .onFailure { delay(1_000) }
        }
        return null
    }

    internal class Factory(
        private val dispatchersApi: DispatchersApi,
        private val settingsRepositoryApi: SettingsRepositoryApi,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java))
                SplashScreenViewModel(dispatchersApi, settingsRepositoryApi) as T
            else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}