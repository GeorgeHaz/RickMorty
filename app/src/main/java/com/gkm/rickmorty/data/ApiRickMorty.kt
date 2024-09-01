package com.gkm.rickmorty.data

import com.gkm.rickmorty.data.response.character.CharacterResponse
import com.gkm.rickmorty.data.response.location.LocationModel
import com.gkm.rickmorty.util.Constans.Companion.POINT_ONE
import com.gkm.rickmorty.util.Constans.Companion.POINT_TWO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRickMorty {

    @GET(POINT_ONE)
    suspend fun getCharacterPage(@Query("page")page:Int): CharacterResponse

    @GET(POINT_TWO)
    suspend fun getEpisode():Response<LocationModel>
}