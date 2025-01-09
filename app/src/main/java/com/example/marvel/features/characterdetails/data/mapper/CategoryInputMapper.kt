package com.example.marvel.features.characterdetails.data.mapper

import com.example.marvel.core.utilities.mappers.BaseMapper
import com.example.marvel.features.characterdetails.data.dto.request.CategoryRequest
import com.example.marvel.features.characterdetails.domain.input.CategoryInput
import javax.inject.Inject

class CategoryInputMapper @Inject constructor() : BaseMapper<CategoryInput, CategoryRequest> {
    override fun map(inputModel: CategoryInput): CategoryRequest {
        return CategoryRequest(
            characterId = inputModel.characterId, queryStrings = mapOf(
                "limit" to inputModel.limit.toString(),
                "offset" to inputModel.offset.toString()
            )
        )

    }

}