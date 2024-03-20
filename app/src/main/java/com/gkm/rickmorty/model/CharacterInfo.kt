package com.gkm.rickmorty.model

import com.google.gson.annotations.SerializedName

data class CharacterInfo(
    @SerializedName("count") val count:String,
    @SerializedName("pages") val pages:String,
    @SerializedName("next") val next:String,
    @SerializedName("prev") val prev:String
)
