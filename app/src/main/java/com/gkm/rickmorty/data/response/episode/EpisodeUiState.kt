package com.gkm.rickmorty.data.response.episode

import com.gkm.rickmorty.data.util.UiState
import com.gkm.rickmorty.presentation.model.episode.EpisodeDto

data class EpisodeUiState(
    val episode: List<EpisodeDto> = emptyList(),
    val uiState: UiState = UiState.LOADING
)