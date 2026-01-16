package com.sergokuzneczow.utilities

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

public interface BaseState

public interface BaseIntent

public interface BaseAction

public abstract class MviViewModel<STATE : BaseState, INTENT : BaseIntent, ACTION : BaseAction>(
    startState: STATE,
    stateDispatcher: CoroutineDispatcher = Dispatchers.Main,
) : ViewModel() {

    private var currentState: STATE = startState

    private val currentStateMutex: Mutex = Mutex()

    private val intentListener: MutableSharedFlow<INTENT> = MutableSharedFlow()

    private val actionListener: MutableSharedFlow<ACTION> = MutableSharedFlow()

    private val stateListener: StateFlow<STATE> = flow {
        actionStartState()?.let { startState ->
            currentStateMutex.withLock {
                currentState = startState
                emit(currentState)
            }
        }

        intentListener.collect { intent ->
            currentStateMutex.withLock {
                object : StateCollector<STATE> {
                    override val current: STATE
                        get() = currentState

                    override suspend fun updateState(block: (old: STATE) -> STATE) {
                        this@flow.emit(block.invoke(current))
                    }
                }.intentListener(intent)
            }
        }
    }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope + stateDispatcher,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = startState,
        )

    public open suspend fun actionStartState(): STATE? = null

    public open suspend fun StateCollector<STATE>.intentListener(intent: INTENT) {
    }

    public fun onState(): StateFlow<STATE> = stateListener

    public fun onAction(): SharedFlow<ACTION> = actionListener

    public suspend fun updateAction(block: () -> ACTION) {
        actionListener.emit(block.invoke())
    }

    public fun updateIntent(intent: INTENT) {
        viewModelScope.launch { intentListener.emit(intent) }
    }
}

@Composable
public fun <STATE : BaseState, INTENT : BaseIntent, ACTION : BaseAction> MviViewModel<STATE, INTENT, ACTION>.collectAction(
    actionsListener: suspend (action: ACTION) -> Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    val actionsFlow: Flow<ACTION> = this.onAction()
    val callback: suspend (ACTION) -> Unit by rememberUpdatedState(newValue = actionsListener)
    LaunchedEffect(actionsFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            actionsFlow.collect { callback.invoke(it) }
        }
    }
}

public val <STATE : BaseState, INTENT : BaseIntent, ACTION : BaseAction> MviViewModel<STATE, INTENT, ACTION>.state: STATE
    @Composable get() = this.onState().collectAsStateWithLifecycle().value

public interface StateCollector<STATE> {
    public val current: STATE
    public suspend fun updateState(block: (STATE) -> STATE)
}

public suspend inline fun <reified STATE, reified ITEM : STATE> StateCollector<STATE>.copyState(block: (old: ITEM) -> STATE) {
    if (current is ITEM) {
        val new: STATE = block.invoke(current as ITEM)
        this.updateState { new }
    }
}