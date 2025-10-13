package com.sergokuzneczow.selected_picture.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelations2UseCase
import com.sergokuzneczow.selected_picture.impl.SelectedPictureIntent.CHANGE_VISIBLE_CURTAIN
import com.sergokuzneczow.selected_picture.impl.di.DaggerSelectedPictureFeatureComponent
import com.sergokuzneczow.selected_picture.impl.di.SelectedPictureFeatureComponent
import com.sergokuzneczow.selected_picture.impl.di.dependenciesProvider
import jakarta.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.concurrent.Volatile

internal class SelectedPictureViewModel(
    @NonUiContext context: Context?,
    private val pictureKey: String,
) : ViewModel() {

    @Inject
    lateinit var getPictureWithRelations2UseCase: GetPictureWithRelations2UseCase

    private val selectedPictureFeatureComponent: SelectedPictureFeatureComponent =
        DaggerSelectedPictureFeatureComponent.builder()
            .setDependencties(context!!.dependenciesProvider.selectedPictureFeatureDependenciesProvider())
            .build()

    @Volatile
    private var currentUiState: SelectedPictureUiState = SelectedPictureUiState.Loading()

    private var currentUiStateMutex: Mutex = Mutex()

    private val intentListener: MutableSharedFlow<SelectedPictureIntent> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    internal val uiState: StateFlow<SelectedPictureUiState> by lazy {
        val sourceFlow: Flow<SelectedPictureUiState> = getPictureWithRelations2UseCase.execute(viewModelScope, pictureKey)
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
                        currentUiState = currentUiState.apply {
                            when (this) {
                                is SelectedPictureUiState.Loading -> this.copy(exceptionMessage = it.message)
                                is SelectedPictureUiState.Success -> this.copy(exceptionMessage = it.message)
                            }
                        }
                    }
                    currentUiState
                }
            }

        val intentListenerFlow: Flow<SelectedPictureUiState> = intentListener.map { intent ->
            currentUiStateMutex.withLock {
                currentUiState = when (intent) {
                    CHANGE_VISIBLE_CURTAIN -> {
                        currentUiState.let { state ->
                            if (state is SelectedPictureUiState.Success) state.copy(curtainVisible = !state.curtainVisible, infoFabVisible = state.curtainVisible) else currentUiState
                        }
                    }
                }
                currentUiState
            }
        }

        merge(sourceFlow, intentListenerFlow).stateIn(
            scope = viewModelScope,
            started =  SharingStarted.WhileSubscribed(5_000),
            initialValue = currentUiState
        )
    }

    init {
        selectedPictureFeatureComponent.inject(this)
    }

    internal fun setIntent(selectedPictureIntent: SelectedPictureIntent) {
        viewModelScope.launch { intentListener.emit(selectedPictureIntent) }
    }

    internal class Factory(
        @NonUiContext private val context: Context,
        private val pictureKey: String,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SelectedPictureViewModel::class.java)) {
                return SelectedPictureViewModel(
                    context = context,
                    pictureKey = pictureKey,
                ) as T
            } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}