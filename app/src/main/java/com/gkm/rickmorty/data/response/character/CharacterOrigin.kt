package com.gkm.rickmorty.data.response.character

import com.google.gson.annotations.SerializedName

data class CharacterOrigin(
    @SerializedName("name") val name:String,
    @SerializedName("url") val url:String
)
