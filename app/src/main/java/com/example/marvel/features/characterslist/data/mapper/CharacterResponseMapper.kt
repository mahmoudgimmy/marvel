package com.example.marvel.features.characterslist.data.mapper

import com.example.marvel.core.utilities.mappers.BaseMapper
import com.example.marvel.features.characterslist.data.dto.response.CharactersResponse
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter
import com.example.marvel.features.characterslist.domain.entity.Section
import java.util.Locale
import javax.inject.Inject

class CharacterResponseMapper @Inject constructor() :
    BaseMapper<List<CharactersResponse>, List<MarvelCharacter>> {
    override fun map(inputModel: List<CharactersResponse>): List<MarvelCharacter> {
        return inputModel.map {
            MarvelCharacter(
                id = it.id ?: 0,
                name = it.name ?: "",
                description = it.description ?: "",
                image =
                it.thumbnail?.path?.replace(
                    "http",
                    "https"
                ) + "." + it.thumbnail?.extension,
                relatedLinks = it.urls?.map {
                    Section(it.type?.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase(
                            Locale.getDefault()
                        ) else char.toString()
                    } ?: "", it.url ?: "")
                } ?: emptyList()

            )
        }
    }
}