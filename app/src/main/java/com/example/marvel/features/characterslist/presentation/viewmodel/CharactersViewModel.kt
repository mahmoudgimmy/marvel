package com.example.marvel.features.characterslist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.marvel.features.characterdetails.presentation.viewmodel.CharactersDetailsSideEffect
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter
import com.example.marvel.features.characterslist.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
class CharactersViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {
    private val _viewState = MutableStateFlow(CharactersListState())
    val viewState: StateFlow<CharactersListState> = _viewState.asStateFlow()

    private val _sideEffect =
        MutableSharedFlow<CharactersListSideEffect>()
    val sideEffect: SharedFlow<CharactersListSideEffect> = _sideEffect.asSharedFlow()

    fun getCharacters() {
        viewModelScope.launch {
            getCharactersUseCase().cachedIn(viewModelScope).collect {
                _viewState.value = _viewState.value.copy(characters = it)
            }
        }
    }

    fun openCharacterDetails(character: MarvelCharacter) {
        viewModelScope.launch {
            _sideEffect.emit(CharactersListSideEffect.OpenCharacterDetails(character))
        }
    }

}