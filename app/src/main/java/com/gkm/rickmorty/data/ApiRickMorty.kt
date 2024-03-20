package com.gkm.rickmorty.data

import com.gkm.rickmorty.model.CharacterModel
import com.gkm.rickmorty.util.Constans.Companion.NUMBER_PAGE
import com.gkm.rickmorty.util.Constans.Companion.POINT_ONE
import retrofit2.Response
import retrofit2.http.GET

interface ApiRickMorty {

    @GET(POINT_ONE + NUMBER_PAGE + 1)
    suspend fun getCharacter():Response<CharacterModel>
}