package com.gkm.rickmorty.data.response.episode

import com.gkm.rickmorty.data.response.UiState
import com.gkm.rickmorty.presentation.model.episode.EpisodeDto

data class EpisodeUiState(
    val episode: EpisodeDto = EpisodeDto(),
    val uiState: UiState = UiState.LOADING
)
