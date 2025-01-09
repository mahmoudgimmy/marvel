package com.example.marvel.features.characterdetails.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvel.features.characterdetails.data.dto.enums.CategoryType
import com.example.marvel.features.characterdetails.domain.entity.Category
import com.example.marvel.features.characterdetails.domain.input.CategoryInput
import com.example.marvel.features.characterdetails.domain.repository.CharacterDetailsRepository
import retrofit2.HttpException
import java.io.IOException

class SeriesPagingSource(
    private val id: Int,
    private val repo: CharacterDetailsRepository
) : PagingSource<Int, Category>() {
    override fun getRefreshKey(state: PagingState<Int, Category>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Category> {
        val pageNumber = params.key ?: 1
        return try {
            val response = repo.getCategory(
                categoryType = CategoryType.SERIES,
                input = CategoryInput(offset = pageNumber, characterId = id)
            )
            LoadResult.Page(
                data = response,
                prevKey = if (pageNumber == 1) null else pageNumber - 20,
                nextKey = if (response.isEmpty()) null else pageNumber + 20
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}