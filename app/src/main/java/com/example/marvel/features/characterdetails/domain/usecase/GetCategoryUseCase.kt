package com.example.marvel.features.characterdetails.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvel.features.characterdetails.data.dto.enums.CategoryType
import com.example.marvel.features.characterdetails.data.pagingsource.ComicsPagingSource
import com.example.marvel.features.characterdetails.data.pagingsource.EventsPagingSource
import com.example.marvel.features.characterdetails.data.pagingsource.SeriesPagingSource
import com.example.marvel.features.characterdetails.data.pagingsource.StoriesPagingSource
import com.example.marvel.features.characterdetails.domain.entity.Category
import com.example.marvel.features.characterdetails.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val repo: CharacterDetailsRepository
) {
    operator fun invoke(
        id: Int, categoryType: CategoryType,
    ):
            Flow<PagingData<Category>> {
        return Pager(PagingConfig(20)) {
            when (categoryType) {
                CategoryType.COMICS -> ComicsPagingSource(id, repo)
                CategoryType.SERIES -> SeriesPagingSource(id, repo)
                CategoryType.STORIES -> StoriesPagingSource(id, repo)
                CategoryType.EVENTS -> EventsPagingSource(id, repo)
            }
        }.flow
    }
}