package com.example.marvel.features.characterdetails.presentation.viewmodel

import androidx.paging.PagingData
import com.example.marvel.features.characterdetails.domain.entity.Category
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter

data class CharacterDetailsState(
    val marvelCharacter: MarvelCharacter,
    val comics: PagingData<Category>? = null,
    val series: PagingData<Category>? = null,
    val stories: PagingData<Category>? = null,
    val events: PagingData<Category>? = null,
)


sealed class CharactersDetailsSideEffect {
    class OpenCategoryImages(val category: Category) : CharactersDetailsSideEffect()
    class OpenExternalLink(val url: String) : CharactersDetailsSideEffect()

}