package com.example.marvel.core.presentation.viewModel

fun interface CoreViewModelInterface<EVENT : CoreState.Event> {
    fun setEvent(event: EVENT)
}