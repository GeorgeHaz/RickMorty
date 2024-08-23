package com.gkm.rickmorty.model.character

data class CharacterModel(
    val info: CharacterInfo,
    val results: List<CharacterResults>
)