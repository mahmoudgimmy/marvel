package com.example.marvel.core.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Abstract base class for ViewModels, providing state management, side effect handling,
 * and utility methods for executing use cases with error handling.
 *
 * @param STATE The type of state managed by the ViewModel.
 * @param SIDE_EFFECT The type of side effects the ViewModel can emit.
 * @param EVENT The type of events the ViewModel can handle.
 * @param initialState The initial state of the ViewModel.
 */
abstract class CoreViewModel<STATE : CoreState.State,
        SIDE_EFFECT : CoreState.SideEffect,
        EVENT : CoreState.Event>(
    initialState: STATE
) : ViewModel(), CoreViewModelInterface<EVENT> {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SIDE_EFFECT>()
    val sideEffect: SharedFlow<SIDE_EFFECT> = _sideEffect.asSharedFlow()

    /**
     * Updates the current state of the ViewModel.
     *
     * This method applies a transformation to the current state, and if the new state is
     * different from the previous one, it updates the state flow.
     *
     * @param newState A lambda that takes the current state and returns the updated state.
     */
    fun updateState(newState: (STATE) -> STATE) {
        if (newState(state.value) != _state.value) {
            _state.value = newState(state.value)
        }
    }

    /**
     * Emits a side effect for consumers to handle.
     *
     * @param effect The side effect to emit.
     */
    fun setSideEffect(effect: SIDE_EFFECT) {
        viewModelScope.launch {
            _sideEffect.emit(effect)
        }
    }

}