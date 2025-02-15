package com.example.marvel.features.characterslist.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.marvel.core.presentation.viewModel.CoreViewModel
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter
import com.example.marvel.features.characterslist.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    CoreViewModel<CharacterContract.State, CharacterContract.SideEffect, CharacterContract.Event>(
        CharacterContract.State()
    ) {


    private fun getCharacters() =
        viewModelScope.launch {
            getCharactersUseCase().cachedIn(viewModelScope).collect { characters ->
                updateState {
                    it.copy(characters = characters)
                }
            }

        }

    private fun openCharacterDetails(character: MarvelCharacter) =
        setSideEffect(CharacterContract.SideEffect.OpenCharacterDetails(character))


    override fun setEvent(event: CharacterContract.Event) {
        when (event) {
            CharacterContract.Event.LoadCharacters -> getCharacters()
            is CharacterContract.Event.OpenCharacterDetails -> openCharacterDetails(event.character)
        }
    }

}