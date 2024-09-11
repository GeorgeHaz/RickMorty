package com.gkm.rickmorty.data.response.episode

import com.gkm.rickmorty.presentation.model.episode.EpisodeDto
import com.google.gson.annotations.SerializedName

data class EpisodeResult(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("air_date") val airDate:String,
    @SerializedName("episode") val episode:String,
    @SerializedName("characters") val characters:List<String>,
    @SerializedName("url") val url:String,
    @SerializedName("created") val created:String
){
    fun toPresentation(): EpisodeDto {
        return EpisodeDto(
            id = id,
            name = name,
            airDate = airDate,
            episode = episode,
            characters = characters,
            url = url,
            created = created
        )
    }
}

