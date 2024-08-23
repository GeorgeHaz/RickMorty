package com.gkm.rickmorty.model.location

import com.google.gson.annotations.SerializedName

data class LocationResult(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:Int,
    @SerializedName("type") val type:Int,
    @SerializedName("dimension") val dimension:Int,
    @SerializedName("residents") val residents:List<String>,
    @SerializedName("url") val url:Int,
    @SerializedName("created") val created:Int
)