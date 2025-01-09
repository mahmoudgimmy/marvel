package com.example.marvel.features.characterslist.domain.repository

import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter
import com.example.marvel.features.characterslist.domain.input.CharacterInput

fun interface CharactersRepository {
    suspend fun getCharacters(
        input: CharacterInput
    ): List<MarvelCharacter>
}