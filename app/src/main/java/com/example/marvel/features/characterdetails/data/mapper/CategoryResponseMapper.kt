package com.example.marvel.features.characterdetails.data.mapper

import com.example.marvel.core.utilities.mappers.BaseMapper
import com.example.marvel.features.characterdetails.data.dto.response.CategoryResponse
import com.example.marvel.features.characterdetails.domain.entity.Category
import com.example.marvel.features.characterslist.data.dto.response.CharactersResponse
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter
import com.example.marvel.features.characterslist.domain.entity.Section
import java.util.Locale
import javax.inject.Inject

class CategoryResponseMapper @Inject constructor() :
    BaseMapper<List<CategoryResponse>, List<Category>> {
    override fun map(inputModel: List<CategoryResponse>): List<Category> {
        return inputModel.map {
            Category(
                id = it.id ?: 0,
                title = it.title ?: "",
                thumbnail =
                it.thumbnail?.path?.replace(
                    "http",
                    "https"
                ) + "." + it.thumbnail?.extension,
                images = it.images?.map { it.path?.replace(
                    "http",
                    "https"
                ) + "." + it.extension
                } ?: emptyList()

            )
        }
    }

}