package com.sergokuzneczow.splash.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.splash.impl.SplashScreenAction
import com.sergokuzneczow.splash.impl.SplashScreenIntent
import com.sergokuzneczow.splash.impl.SplashScreenState
import com.sergokuzneczow.utilities.DispatchersApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.plus
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

internal class SplashScreenViewModel(
    dispatchersApi: DispatchersApi,
    private val settingsRepositoryApi: SettingsRepositoryApi,
) : ViewModel(), ContainerHost<SplashScreenState, SplashScreenAction> {

    override val container: Container<SplashScreenState, SplashScreenAction> = (viewModelScope + dispatchersApi.default).container(SplashScreenState.CheckingFirstLaunch)

    init {
        dispatch(SplashScreenIntent.CheckLaunchStatus)
    }

    fun dispatch(intent: SplashScreenIntent) {
        when (intent) {
            SplashScreenIntent.CheckLaunchStatus -> intent {
                runCatching { settingsRepositoryApi.getSettings() }
                    .onSuccess { settings: ApplicationSettings? ->
                        if (settings == null) postSideEffect(SplashScreenAction.IsFirstLaunch)
                        else postSideEffect(SplashScreenAction.IsNotFirstLaunch(settings))
                    }
                    .onFailure {
                        delay(500)
                        dispatch(intent)
                    }
            }
        }
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