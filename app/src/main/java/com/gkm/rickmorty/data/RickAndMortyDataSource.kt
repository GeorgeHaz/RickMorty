package com.gkm.rickmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class RickAndMortyDataSource @Inject constructor(
    private val apiRickMorty: ApiRickMorty,
    private val name:String?=null,
):PagingSource<Int, CharacterModel>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {
        return  state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
        return try{
            val page = params.key ?: 1
            val response = apiRickMorty.getCharacterPage(page, name)
            val data = response.results

            val prevKey = if(page == 1) null else page - 1
            val nextKey = if(response.info.next != null) page + 1 else null

            LoadResult.Page(
                data = data
                    .map {it.toPresentation()},
                prevKey = prevKey,
                nextKey = nextKey
            )
        }catch (e: HttpException) {
            if (e.code() == 404) {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                LoadResult.Error(e)
            }
        }catch (e:IOException){
            LoadResult.Error(e)
        }
    }
}