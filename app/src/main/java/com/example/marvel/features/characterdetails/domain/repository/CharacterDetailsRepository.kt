package com.example.marvel.features.characterdetails.domain.repository

import com.example.marvel.features.characterdetails.data.dto.enums.CategoryType
import com.example.marvel.features.characterdetails.domain.entity.Category
import com.example.marvel.features.characterdetails.domain.input.CategoryInput

fun interface CharacterDetailsRepository {
    suspend fun getCategory(
        categoryType:CategoryType,
        input: CategoryInput
    ): List<Category>
}