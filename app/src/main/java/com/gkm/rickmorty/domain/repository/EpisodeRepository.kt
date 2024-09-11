package com.gkm.rickmorty.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gkm.rickmorty.data.network.ApiRickMorty
import com.gkm.rickmorty.data.pagging.EpisodeDataSource
import com.gkm.rickmorty.presentation.model.episode.EpisodeDto
import com.gkm.rickmorty.util.Constans.Companion.MAX_ITEMS
import com.gkm.rickmorty.util.Constans.Companion.PREFETCH_ITEMS
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeRepository @Inject constructor( val apiRickMorty: ApiRickMorty){

    fun getAllEpisode(name:String?=null): Flow<PagingData<EpisodeDto>> {
        return Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                EpisodeDataSource(apiRickMorty)
            }).flow
    }

    suspend fun getEpisodeDetail(episode:String): Result<List<EpisodeDto>> {
        val episodeDetail =  apiRickMorty.getEpisodeDetail(episode)
        return Result.success(listOf(episodeDetail.toPresentation()))
    }
}