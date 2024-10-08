package com.gkm.rickmorty.data.network

import com.gkm.rickmorty.data.response.character.CharacterResponse
import com.gkm.rickmorty.data.response.character.CharacterResults
import com.gkm.rickmorty.data.response.episode.EpisodeResponse
import com.gkm.rickmorty.data.response.episode.EpisodeResult
import com.gkm.rickmorty.data.response.location.LocationResponse
import com.gkm.rickmorty.data.util.Constans.Companion.POINT_ONE
import com.gkm.rickmorty.data.util.Constans.Companion.POINT_THREE
import com.gkm.rickmorty.data.util.Constans.Companion.POINT_TWO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRickMorty {

    @GET(POINT_ONE)
    suspend fun getCharacterPage(
        @Query("page")page:Int,
        @Query("name")name:String?=null
    ): CharacterResponse

    @GET("$POINT_ONE/{id_Character}")
    suspend fun getCharacterDetail(
        @Path("id_Character")idCharacter:Int = 0
    ):CharacterResults

    @GET(POINT_TWO)
    suspend fun getLocation():Response<LocationResponse>

    @GET(POINT_THREE)
    suspend fun getEpisode(
        @Query("page")page:Int,
    ):EpisodeResponse

    @GET("$POINT_THREE/{id_Episode}")
    suspend fun getEpisodeOneDetail(
        @Path("id_Episode")idEpisode:String
    ):EpisodeResult

    @GET("$POINT_THREE/{ids_Episode}")
    suspend fun getEpisodeDetail(
        @Path("ids_Episode")idsEpisode:List<String>
    ): List<EpisodeResult>
}