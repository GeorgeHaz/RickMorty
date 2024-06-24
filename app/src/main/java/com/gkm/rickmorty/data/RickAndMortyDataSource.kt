package com.gkm.rickmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gkm.rickmorty.model.CharacterResults
import com.gkm.rickmorty.repository.CharacterRepository

class RickAndMortyDataSource(
    private val repository: CharacterRepository
):PagingSource<Int, CharacterResults>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterResults>): Int? {
        return state.anchorPosition?.let{position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResults> {
        return  try{
            val page = params.key ?: 1
            val response = repository.getCharacterPage(page, 10)
            val data = response.results

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = if(response.results.isNotEmpty()) page + 1 else null
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

}