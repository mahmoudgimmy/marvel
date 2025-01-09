package com.example.marvel.features.characterslist.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter
import com.example.marvel.features.characterslist.domain.input.CharacterInput
import com.example.marvel.features.characterslist.domain.repository.CharactersRepository
import retrofit2.HttpException
import java.io.IOException

class CharactersPagingSource(
    private val repo: CharactersRepository
) : PagingSource<Int, MarvelCharacter>() {
    override fun getRefreshKey(state: PagingState<Int, MarvelCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarvelCharacter> {
        val pageNumber = params.key ?: 1
        return try {
            val response = repo.getCharacters(CharacterInput(offset = pageNumber))
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