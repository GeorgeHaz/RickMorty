package com.gkm.rickmorty.data.response.location

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("info")val info: LocationInfo,
    @SerializedName("results")val results: List<LocationResult>
)
