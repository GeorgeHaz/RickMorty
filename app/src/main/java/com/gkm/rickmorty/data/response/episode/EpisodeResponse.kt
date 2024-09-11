package com.gkm.rickmorty.data.response.episode

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("info") val info: EpisodeInfo,
    @SerializedName("results") val results: List<EpisodeResult>
)
