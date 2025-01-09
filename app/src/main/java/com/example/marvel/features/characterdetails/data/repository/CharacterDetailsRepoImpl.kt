package com.example.marvel.features.characterdetails.data.repository

import com.example.marvel.core.network.services.MarvelServices
import com.example.marvel.features.characterdetails.data.dto.enums.CategoryType
import com.example.marvel.features.characterdetails.data.mapper.CategoryInputMapper
import com.example.marvel.features.characterdetails.data.mapper.CategoryResponseMapper
import com.example.marvel.features.characterdetails.domain.entity.Category
import com.example.marvel.features.characterdetails.domain.input.CategoryInput
import com.example.marvel.features.characterdetails.domain.repository.CharacterDetailsRepository
import javax.inject.Inject

class CharacterDetailsRepoImpl @Inject constructor(
    private val apiServices: MarvelServices,
    private val categoryInputMapper: CategoryInputMapper,
    private val categoryResponseMapper: CategoryResponseMapper
) : CharacterDetailsRepository {
    override suspend fun getCategory(
        categoryType: CategoryType,
        input: CategoryInput
    ): List<Category> {
        val inputModel = categoryInputMapper.map(input)
        val response = when (categoryType) {
            CategoryType.COMICS -> apiServices.getComics(
                inputModel.characterId,
                inputModel.queryStrings
            )

            CategoryType.SERIES -> apiServices.getSeries(
                inputModel.characterId,
                inputModel.queryStrings
            )

            CategoryType.STORIES -> apiServices.getStories(
                inputModel.characterId,
                inputModel.queryStrings
            )

            CategoryType.EVENTS -> apiServices.getEvents(
                inputModel.characterId,
                inputModel.queryStrings
            )
        }

        return if (response.isSuccessful)
            if(response.body()?.data?.results?.isEmpty() == true)
                return emptyList()
        else
            response.body()?.data?.results?.let {
            categoryResponseMapper.map(it)
        } ?: emptyList() else emptyList()
    }
}