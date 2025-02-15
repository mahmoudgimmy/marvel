package com.example.marvel.features.characterdetails.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.marvel.core.presentation.viewModel.CoreViewModel
import com.example.marvel.core.utilities.annotations.IoDispatcher
import com.example.marvel.features.characterdetails.data.dto.enums.CategoryType
import com.example.marvel.features.characterdetails.domain.entity.Category
import com.example.marvel.features.characterdetails.domain.usecase.GetCategoryUseCase
import com.example.marvel.features.characterdetails.presentation.view.CharacterDetailsFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @IoDispatcher override val coroutineDispatcher: CoroutineDispatcher,
    private val getCategoryUseCase: GetCategoryUseCase
) : CoreViewModel<CharacterDetailsContract.State, CharacterDetailsContract.SideEffect, CharacterDetailsContract.Event>(
    CharacterDetailsContract.State(
        marvelCharacter =
        CharacterDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle).character
    )
) {
    val args = CharacterDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private fun getAllCategories() {
        viewModelScope.apply {
            launch(coroutineDispatcher) {
                getCategoryUseCase.invoke(args.character.id, CategoryType.COMICS)
                    .cachedIn(viewModelScope)
                    .collectLatest { comics ->
                        updateState {
                            it.copy(comics = comics)
                        }
                    }
            }
            launch(coroutineDispatcher) {
                getCategoryUseCase.invoke(args.character.id, CategoryType.STORIES)
                    .cachedIn(viewModelScope)
                    .collectLatest { stories ->
                        updateState {
                            it.copy(stories = stories)
                        }
                    }
            }
            launch(coroutineDispatcher) {
                getCategoryUseCase.invoke(args.character.id, CategoryType.SERIES)
                    .cachedIn(viewModelScope)
                    .collectLatest { series ->
                        updateState {
                            it.copy(series = series)
                        }
                    }
            }
            launch(coroutineDispatcher) {
                getCategoryUseCase.invoke(args.character.id, CategoryType.EVENTS)
                    .cachedIn(viewModelScope)
                    .collectLatest { events ->
                        updateState {
                            it.copy(events = events)
                        }
                    }
            }
        }
    }

    private fun openCategoryImages(category: Category) =
        setSideEffect(CharacterDetailsContract.SideEffect.OpenCategoryImages(category))


    private fun openExternalLink(url: String) =
        setSideEffect(CharacterDetailsContract.SideEffect.OpenExternalLink(url))


    override fun setEvent(event: CharacterDetailsContract.Event) {
        when (event) {
            CharacterDetailsContract.Event.LoadCategories -> getAllCategories()
            is CharacterDetailsContract.Event.OpenCategoryImages -> openCategoryImages(event.category)
            is CharacterDetailsContract.Event.OpenExternalLink -> openExternalLink(event.url)
        }
    }
}