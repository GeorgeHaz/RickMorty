package com.gkm.rickmorty.model

data class CharacterModel(
    val info:CharacterInfo,
    val results: List<CharacterResults>
)