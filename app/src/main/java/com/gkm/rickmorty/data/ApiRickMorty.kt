package com.gkm.rickmorty.data

import com.gkm.rickmorty.model.CharacterModel
import com.gkm.rickmorty.util.Constans.Companion.POINT_ONE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRickMorty {

    @GET(POINT_ONE)
    suspend fun getCharacter():Response<CharacterModel>

    @GET(POINT_ONE)
    suspend fun getCharacterPage(@Query("page")page:Int, @Query("page_size")pageSize:Int):CharacterModel
}