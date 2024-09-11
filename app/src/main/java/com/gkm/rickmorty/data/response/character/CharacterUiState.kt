package com.gkm.rickmorty.data.response.character

import com.gkm.rickmorty.data.response.UiState
import com.gkm.rickmorty.presentation.model.character.CharacterModel

data class CharacterUiState(
    val character: CharacterModel = CharacterModel(),
    val uiState: UiState = UiState.LOADING
)
