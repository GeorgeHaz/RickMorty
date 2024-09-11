package com.gkm.rickmorty.domain.useCase.episode

import androidx.paging.PagingData
import com.gkm.rickmorty.domain.repository.EpisodeRepository
import com.gkm.rickmorty.presentation.model.episode.EpisodeDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeUseCase @Inject constructor(private val episodeRepository: EpisodeRepository) {

    operator fun invoke(): Flow<PagingData<EpisodeDto>> {
        return episodeRepository.getAllEpisode()
    }

    suspend operator fun invoke(episode:String) = episodeRepository.getEpisodeDetail(episode)
}