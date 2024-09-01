package com.gkm.rickmorty.data.response.character

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")val info: CharacterInfo,
    @SerializedName("results")val results: List<CharacterResults>
)