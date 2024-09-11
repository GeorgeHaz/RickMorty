package com.gkm.rickmorty.data.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gkm.rickmorty.data.network.ApiRickMorty
import com.gkm.rickmorty.presentation.model.episode.EpisodeDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EpisodeDataSource @Inject constructor(
    private val apiRickMorty: ApiRickMorty,
): PagingSource<Int, EpisodeDto>()
{
    override fun getRefreshKey(state: PagingState<Int, EpisodeDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeDto> {
        return try{
            val page = params.key ?: 1
            val response = apiRickMorty.getEpisode(page)
            val data = response.results

            val prevKey = if(page == 1) null else page - 1
            val nextKey = if(response.info.next != null) page + 1 else null

            LoadResult.Page(
                data = data
                    .map {it.toPresentation()},
                prevKey = prevKey,
                nextKey = nextKey
            )
        }catch (e: HttpException){
            if(e.code() == 404){
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }else{
                LoadResult.Error(e)
            }
        }catch (e: IOException){
            LoadResult.Error(e)
        }
    }

}