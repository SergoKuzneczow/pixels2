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
import kotlin.time.Duration.Companion.seconds

internal class ApplicationSetupScreenViewModel(
    private val settingsRepositoryApi: SettingsRepositoryApi,
) : ViewModel() {

    private var currentUiState: ApplicationSetupScreenUiState = ApplicationSetupScreenUiState.Loading

    private var currentUiStateMutex: Mutex = Mutex()

    private val intentListener: MutableSharedFlow<ApplicationSetupScreenIntent> = MutableSharedFlow()

    val uiState: StateFlow<ApplicationSetupScreenUiState> = flow {
        runCatching {
            val currentSettings: ApplicationSettings? = settingsRepositoryApi.getSettings()
            if (currentSettings == null) {
                runCatching { settingsRepositoryApi.setSettings(ApplicationSettings.DEFAULT) }
                    .onFailure {
                        delay(1.seconds)
                        intentListener.emit(ApplicationSetupScreenIntent.SaveDefaultSettings)
                    }
            }
            settingsRepositoryApi.getSettings() ?: throw IllegalStateException("Application settings must be initialize.")
        }.onSuccess { applicationSettings ->
            updateCurrentUiState { ApplicationSetupScreenUiState.SelectingTheme(applicationSettings.systemSettings.themeState) }
        }.onFailure {
            intentListener.emit(ApplicationSetupScreenIntent.GetCurrentThemeState)
        }

        intentListener.collect { intent ->
            when (intent) {
                ApplicationSetupScreenIntent.Skip -> updateCurrentUiState { ApplicationSetupScreenUiState.ApplicationSetupCompleted }

                ApplicationSetupScreenIntent.Done -> updateCurrentUiState { ApplicationSetupScreenUiState.ApplicationSetupCompleted }

                ApplicationSetupScreenIntent.SaveDefaultSettings -> {
                    runCatching { settingsRepositoryApi.setSettings(ApplicationSettings.DEFAULT) }
                        .onSuccess { intentListener.emit(ApplicationSetupScreenIntent.GetCurrentThemeState) }
                        .onFailure {
                            delay(1.seconds)
                            intentListener.emit(ApplicationSetupScreenIntent.SaveDefaultSettings)
                        }
                }

                ApplicationSetupScreenIntent.GetCurrentThemeState -> {
                    runCatching { settingsRepositoryApi.getSettings() ?: throw IllegalStateException("ApplicationSettings can't be null.") }
                        .onSuccess { updateCurrentUiState { ApplicationSetupScreenUiState.SelectingTheme(it.systemSettings.themeState) } }
                        .onFailure { intentListener.emit(ApplicationSetupScreenIntent.GetCurrentThemeState) }
                }

                is ApplicationSetupScreenIntent.ThemeSelected -> {
                    updateSettings(
                        settingsMapping = { it.copy(systemSettings = it.systemSettings.copy(themeState = intent.newThemeState)) },
                        completed = {}
                    )
                }
            }
        }
    }.flowOn(Dispatchers.IO)
        .stateIn(
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