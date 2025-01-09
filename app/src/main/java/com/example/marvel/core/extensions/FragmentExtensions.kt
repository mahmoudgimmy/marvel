package com.example.marvel.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.collectSharedFlow(
    sharedFlow: SharedFlow<T>,
    block: (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        sharedFlow.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).collectLatest {
            block(it)
        }
    }
}

fun <T> Fragment.collectFlow(
    flow: Flow<T>,
    block: (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).collectLatest {
            block(it)
        }
    }
}