package com.example.marvel.features.characterdetails.presentation.viewmodel

import androidx.paging.PagingData
import com.example.marvel.core.presentation.viewModel.CoreState
import com.example.marvel.features.characterdetails.domain.entity.Category
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter

class CharacterDetailsContract {

    data class State(
        val marvelCharacter: MarvelCharacter,
        val comics: PagingData<Category>? = null,
        val series: PagingData<Category>? = null,
        val stories: PagingData<Category>? = null,
        val events: PagingData<Category>? = null,
    ) : CoreState.State

    sealed class SideEffect : CoreState.SideEffect {
        class OpenCategoryImages(val category: Category) : SideEffect()
        class OpenExternalLink(val url: String) : SideEffect()
    }

    sealed class Event : CoreState.Event {
        object LoadCategories : Event()
        class OpenCategoryImages(val category: Category) : Event()
        class OpenExternalLink(val url: String) : Event()
    }
}
