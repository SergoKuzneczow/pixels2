package com.sergokuzneczow.settings.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.settings.impl.SettingsScreenIntent
import com.sergokuzneczow.settings.impl.SettingsScreenIntent.ChangeThemeState
import com.sergokuzneczow.settings.impl.SettingsScreenIntent.SetSettings
import com.sergokuzneczow.settings.impl.SettingsScreenSideEffect
import com.sergokuzneczow.settings.impl.SettingsScreenState
import com.sergokuzneczow.settings.impl.SettingsScreenState.Success
import com.sergokuzneczow.utilities.DispatchersApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.container
import kotlin.time.Duration.Companion.seconds

internal class SettingsScreenViewModel(
    dispatchersApi: DispatchersApi,
    private val settingsRepositoryApi: SettingsRepositoryApi,
) : ViewModel(), ContainerHost<SettingsScreenState, SettingsScreenSideEffect> {

    override val container: Container<SettingsScreenState, SettingsScreenSideEffect> = (viewModelScope + dispatchersApi.default).container(SettingsScreenState.Loading)

    init {
        viewModelScope.launch(dispatchersApi.io) {
            settingsRepositoryApi.getSettings()?.let { dispatch(SetSettings(it.systemSettings.themeState)) }
        }
    }

    @OptIn(OrbitExperimental::class)
    fun dispatch(intent: SettingsScreenIntent) {
        when (intent) {
            is SetSettings -> intent {
                reduce { Success(intent.themeState, changingThemeState = false) }
            }

            is ChangeThemeState -> intent {
                runOn<Success> { reduce { state.copy(changingThemeState = true) } }
                val new: ApplicationSettings = settingsRepositoryApi.changeThemeState(intent.themeState)
                runOn<Success> { reduce { state.copy(themeState = new.systemSettings.themeState, changingThemeState = false) } }
            }
        }
    }


    internal class Factory(
        private val dispatchersApi: DispatchersApi,
        private val settingsRepositoryApi: SettingsRepositoryApi,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(SettingsScreenViewModel::class.java)) SettingsScreenViewModel(dispatchersApi, settingsRepositoryApi) as T
            else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}