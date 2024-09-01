package com.gkm.rickmorty.data.response.location

data class LocationModel(
    val info: LocationInfo,
    val results: List<LocationResult>
)
