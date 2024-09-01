package com.gkm.rickmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import okio.IOException
import javax.inject.Inject

class RickAndMortyDataSource @Inject constructor(
    private val apiRickMorty: ApiRickMorty):PagingSource<Int, CharacterModel>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {
        return  state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
        return try{
            val page = params.key ?: 1
            val response = apiRickMorty.getCharacterPage(page)
            val data = response.results

            val prevKey = if(page>0) page - 1 else null
            val nextKey = if(response.info.next != null) page + 1 else null

            LoadResult.Page(
                data = data
                    .map {it.toPresentation()},
                prevKey = prevKey,
                nextKey = nextKey
            )
        }catch (e:IOException){
            LoadResult.Error(e)
        }
    }
}