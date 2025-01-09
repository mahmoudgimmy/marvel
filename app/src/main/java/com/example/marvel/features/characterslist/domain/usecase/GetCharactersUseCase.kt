package com.example.marvel.features.characterslist.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvel.features.characterslist.data.pagingsource.CharactersPagingSource
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter
import com.example.marvel.features.characterslist.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repo: CharactersRepository
) {
    operator fun invoke():
            Flow<PagingData<MarvelCharacter>> {
        return Pager(PagingConfig(20)) {
            CharactersPagingSource(repo)
        }.flow
    }
}