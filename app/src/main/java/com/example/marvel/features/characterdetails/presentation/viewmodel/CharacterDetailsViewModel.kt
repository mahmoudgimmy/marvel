package com.example.marvel.features.characterdetails.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.marvel.features.characterdetails.data.dto.enums.CategoryType
import com.example.marvel.features.characterdetails.domain.entity.Category
import com.example.marvel.features.characterdetails.domain.usecase.GetCategoryUseCase
import com.example.marvel.features.characterdetails.presentation.view.CharacterDetailsFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {
    val args = CharacterDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val _viewState = MutableStateFlow(
        CharacterDetailsState(
            args.character
        )
    )
    val viewState: StateFlow<CharacterDetailsState> = _viewState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<CharactersDetailsSideEffect>()
    val sideEffect: SharedFlow<CharactersDetailsSideEffect> = _sideEffect.asSharedFlow()

    fun getAllCategories() {
        viewModelScope.apply {

            launch {
                getCategoryUseCase.invoke(args.character.id, CategoryType.COMICS)
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        _viewState.value = _viewState.value.copy(comics = it)
                    }
            }
            launch {
                getCategoryUseCase.invoke(args.character.id, CategoryType.STORIES)
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        _viewState.value = _viewState.value.copy(stories = it)
                    }
            }
            launch {
                getCategoryUseCase.invoke(args.character.id, CategoryType.SERIES)
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        _viewState.value = _viewState.value.copy(series = it)
                    }
            }
            launch {
                getCategoryUseCase.invoke(args.character.id, CategoryType.EVENTS)
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        _viewState.value = _viewState.value.copy(events = it)
                    }
            }
        }
    }

    fun openCategoryImages(category: Category) {
        viewModelScope.launch {
            _sideEffect.emit(CharactersDetailsSideEffect.OpenCategoryImages(category))

        }
    }

    fun openExternalLink(url: String) {
        viewModelScope.launch {
            _sideEffect.emit(CharactersDetailsSideEffect.OpenExternalLink(url))
        }
    }
}