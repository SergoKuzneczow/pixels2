package com.sergokuzneczow.settings.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.settings.impl.SettingsScreenIntent
import com.sergokuzneczow.settings.impl.SettingsScreenUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class SettingsScreenViewModel(
    private val settingsRepositoryApi: SettingsRepositoryApi,
) : ViewModel() {

    private var currentUiState: SettingsScreenUiState = SettingsScreenUiState.Loading()

    private var currentUiStateMutex: Mutex = Mutex()

    private val dataSourceFlow: Flow<SettingsScreenUiState> =
        settingsRepositoryApi.getSettingsAsFlow()
            .map { settings ->
                currentUiStateMutex.withLock {
                    currentUiState = settings?.let { SettingsScreenUiState.Success(settings = it, isChanging = false) } ?: currentUiState
                    return@map currentUiState
                }
            }

    private val intentListener: MutableSharedFlow<SettingsScreenIntent> = MutableSharedFlow()

    private val intentFlow: Flow<SettingsScreenUiState> = flow {
        intentListener.collect { intent ->
            currentUiStateMutex.withLock {
                when (intent) {
                    is SettingsScreenIntent.ChangeSettingsIntent -> {
                        currentUiState.isChanging = true
                        settingsRepositoryApi.setSettings(intent.newApplicationSettings)
                    }
                }
            }
        }
    }

    internal val uiState: StateFlow<SettingsScreenUiState> =
        merge(dataSourceFlow, intentFlow).flowOn(Dispatchers.IO).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = currentUiState,
        )

    internal fun setIntent(intent: SettingsScreenIntent) {
        viewModelScope.launch { intentListener.emit(intent) }
    }
}