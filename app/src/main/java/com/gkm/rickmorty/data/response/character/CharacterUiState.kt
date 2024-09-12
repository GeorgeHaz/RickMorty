package com.gkm.rickmorty.data.response.character

import com.gkm.rickmorty.data.util.UiState
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.presentation.model.episode.EpisodeDto

data class CharacterUiState(
    val character: CharacterModel = CharacterModel(),
    val episode: List<EpisodeDto> = emptyList(),
    val uiState: UiState = UiState.LOADING
)
