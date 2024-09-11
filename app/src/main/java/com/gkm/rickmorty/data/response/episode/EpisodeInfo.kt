package com.gkm.rickmorty.data.response.episode

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class EpisodeInfo(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
)
