package com.example.marvel.features.characterslist.presentation.viewmodel

import androidx.paging.PagingData
import com.example.marvel.core.presentation.viewModel.CoreState
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter


class CharacterContract {
    data class State(val characters: PagingData<MarvelCharacter>? = null) : CoreState.State

    sealed class SideEffect : CoreState.SideEffect {
        data class OpenCharacterDetails(val character: MarvelCharacter) : SideEffect()
    }

    sealed class Event : CoreState.Event {
        data object LoadCharacters : Event()
        data class OpenCharacterDetails(val character: MarvelCharacter) : Event()
    }

}
