package com.example.marvel.features.characterslist.data.repository

import com.example.marvel.core.network.services.MarvelServices
import com.example.marvel.features.characterslist.data.mapper.CharacterInputMapper
import com.example.marvel.features.characterslist.data.mapper.CharacterResponseMapper
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter
import com.example.marvel.features.characterslist.domain.input.CharacterInput
import com.example.marvel.features.characterslist.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val apiServices: MarvelServices,
    private val characterInputMapper: CharacterInputMapper,
    private val characterResponseMapper: CharacterResponseMapper
) : CharactersRepository {
    override suspend fun getCharacters(input: CharacterInput): List<MarvelCharacter> {
        val result = apiServices.getCharacters(characterInputMapper.map(input))
        return if (result.isSuccessful) result.body()?.data?.results?.let {
            characterResponseMapper.map(it)
        } ?: emptyList() else emptyList()
    }
}