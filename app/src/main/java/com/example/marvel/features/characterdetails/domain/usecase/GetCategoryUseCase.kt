package com.example.marvel.features.characterdetails.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvel.features.characterdetails.data.dto.enums.CategoryType
import com.example.marvel.features.characterdetails.data.pagingsource.CategoryTypePagingSource
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
            CategoryTypePagingSource(id, categoryType, repo)
        }.flow
    }
}