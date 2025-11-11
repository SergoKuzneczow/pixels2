package com.sergokuzneczow.application_setup.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenIntent
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenUiState
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class ApplicationSetupScreenViewModel(
    private val settingsRepositoryApi: SettingsRepositoryApi,
) : ViewModel() {

    private var currentUiState: ApplicationSetupScreenUiState = ApplicationSetupScreenUiState.Loading

    private var currentUiStateMutex: Mutex = Mutex()

    private val intentListener: MutableSharedFlow<ApplicationSetupScreenIntent> = MutableSharedFlow()

    val uiState: StateFlow<ApplicationSetupScreenUiState> = flow {

        var settingsChecked = false
        while (!settingsChecked) {
            runCatching {
                settingsRepositoryApi.setSettings(ApplicationSettings.DEFAULT)
                settingsRepositoryApi.getSettings() ?: throw IllegalStateException("Application settings can't be null.")
            }.onSuccess { applicationSettings ->
                settingsChecked = true
                updateCurrentUiState { ApplicationSetupScreenUiState.SelectingTheme(applicationSettings.systemSettings.themeState) }
            }.onFailure {
                delay(1_000)
            }
        }

        intentListener.collect { intent ->
            when (intent) {
                is ApplicationSetupScreenIntent.SaveDefaultSetting -> {
                    updateSettings(
                        settingsMapping = { it },
                        completed = { intent.completed.invoke() }
                    )
                }

                is ApplicationSetupScreenIntent.SaveThemeSetting -> {
                    updateSettings(
                        settingsMapping = { it.copy(systemSettings = it.systemSettings.copy(themeState = intent.newThemeState)) },
                        completed = { intent.completed.invoke() }
                    )
                }
            }
        }
    }.flowOn(Dispatchers.IO).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = currentUiState,
    )

    internal fun setIntent(intent: ApplicationSetupScreenIntent) {
        viewModelScope.launch { intentListener.emit(intent) }
    }

    private suspend fun FlowCollector<ApplicationSetupScreenUiState>.updateCurrentUiState(block: () -> ApplicationSetupScreenUiState) {
        currentUiStateMutex.withLock {
            currentUiState = block.invoke()
            emit(currentUiState)
        }
    }

    private suspend fun updateSettings(
        settingsMapping: suspend (applicationSettings: ApplicationSettings) -> ApplicationSettings,
        completed: suspend () -> Unit,
    ) {
        val currentSettings: ApplicationSettings? = settingsRepositoryApi.getSettings()
        currentSettings?.let {
            runCatching { settingsRepositoryApi.setSettings(settingsMapping.invoke(it)) }
                .onSuccess { completed.invoke() }
                .onFailure {
                    delay(3_000)
                    updateSettings(settingsMapping, completed)
                }
        }
    }
}