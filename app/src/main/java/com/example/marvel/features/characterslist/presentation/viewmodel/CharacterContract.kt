package com.example.marvel.features.characterslist.presentation.viewmodel

import androidx.paging.PagingData
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter

data class CharactersListState(
    val characters: PagingData<MarvelCharacter>? = null,
)


sealed class CharactersListSideEffect  {
    class OpenCharacterDetails(val character: MarvelCharacter) : CharactersListSideEffect()
}