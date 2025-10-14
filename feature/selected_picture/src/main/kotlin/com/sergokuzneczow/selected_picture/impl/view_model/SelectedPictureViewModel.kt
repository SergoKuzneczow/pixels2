package com.sergokuzneczow.selected_picture.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2UseCase
import com.sergokuzneczow.selected_picture.impl.SelectedPictureIntent
import com.sergokuzneczow.selected_picture.impl.SelectedPictureUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.concurrent.Volatile

internal class SelectedPictureViewModel(
    private val pictureKey: String,
    getPictureWithRelations2UseCase: GetPictureWithRelations2UseCase,
) : ViewModel() {

    @Volatile
    private var currentUiState: SelectedPictureUiState = SelectedPictureUiState.Loading()

    private var currentUiStateMutex: Mutex = Mutex()

    private val sourceFlow: Flow<SelectedPictureUiState> =
        getPictureWithRelations2UseCase.execute(viewModelScope, pictureKey)
            .map { result ->
                currentUiStateMutex.withLock {
                    result.onSuccess {
                        currentUiState = SelectedPictureUiState.Success(
                            pictureKey = it.data.picture.key,
                            picturePath = it.data.picture.path,
                            curtainVisible = false,
                            infoFabVisible = true,
                        )
                    }.onFailure {
                        currentUiState = currentUiState.run {
                            when (this) {
                                is SelectedPictureUiState.Loading -> this.copy(exceptionMessage = it.message)
                                is SelectedPictureUiState.Success -> this.copy(exceptionMessage = it.message)
                            }
                        }
                    }
                    currentUiState
                }
            }.flowOn(Dispatchers.Default)

    private val intentListener: MutableSharedFlow<SelectedPictureIntent> = MutableSharedFlow()

    private val intentListenerFlow: Flow<SelectedPictureUiState> =
        intentListener.map { intent ->
            currentUiStateMutex.withLock {
                currentUiState = currentUiState.changeUiState(intent)
                currentUiState
            }
        }

    internal val uiState: StateFlow<SelectedPictureUiState> =
        merge(sourceFlow, intentListenerFlow)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(5_000),
                initialValue = currentUiState
            )

    internal fun setIntent(selectedPictureIntent: SelectedPictureIntent) {
        viewModelScope.launch { intentListener.emit(selectedPictureIntent) }
    }

    private fun SelectedPictureUiState.changeUiState(intent: SelectedPictureIntent): SelectedPictureUiState {
        return when (intent) {
            SelectedPictureIntent.CHANGE_VISIBLE_CURTAIN -> {
                if (this is SelectedPictureUiState.Success) this.copy(curtainVisible = !this.curtainVisible, infoFabVisible = this.curtainVisible) else currentUiState
            }
        }
    }
}