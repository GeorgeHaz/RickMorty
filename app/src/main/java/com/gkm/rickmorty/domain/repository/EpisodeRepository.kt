package com.gkm.rickmorty.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gkm.rickmorty.data.network.ApiRickMorty
import com.gkm.rickmorty.data.pagging.EpisodeDataSource
import com.gkm.rickmorty.presentation.model.episode.EpisodeDto
import com.gkm.rickmorty.data.util.Constans.Companion.MAX_ITEMS
import com.gkm.rickmorty.data.util.Constans.Companion.PREFETCH_ITEMS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val apiRickMorty: ApiRickMorty,
) {

    suspend fun getAllEpisode(name: String? = null): Flow<PagingData<EpisodeDto>> {
        return withContext(Dispatchers.IO) {
            Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
                pagingSourceFactory = {
                    EpisodeDataSource(apiRickMorty)
                }
            ).flow
        }
    }

    suspend fun getEpisodeOneDetail(episode: String): EpisodeDto {
        return withContext(Dispatchers.IO) {
            apiRickMorty.getEpisodeOneDetail(episode).toPresentation()
        }
    }

    suspend fun getEpisodeDetail(episodeUrls: List<String>): List<EpisodeDto> {
        return withContext(Dispatchers.IO) {
            val ids = episodeUrls.map { url -> url.split("/").last() }
            if(ids.size == 1){
                val episode = apiRickMorty.getEpisodeOneDetail(ids.first())
                listOf(episode.toPresentation())
            }else{
                val episodes = apiRickMorty.getEpisodeDetail(ids)
                episodes.map { it.toPresentation() }
            }
        }
    }
}