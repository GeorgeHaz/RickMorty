package com.gkm.rickmorty.data.response.character

import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.google.gson.annotations.SerializedName

data class CharacterResults(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("status") val status:String,
    @SerializedName("species") val species:String,
    @SerializedName("type") val type:String,
    @SerializedName("gender") val gender:String,
    @SerializedName("origin") val origin: CharacterOrigin,
    @SerializedName("location") val location: CharacterLocation,
    @SerializedName("image") val image:String,
    @SerializedName("episode") val episode:List<String>,
    @SerializedName("url") val url:String,
    @SerializedName("created") val created:String
){
    fun toPresentation():CharacterModel{
        return CharacterModel(
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            originName = origin.name,
            originUrl = origin.url,
            locationName = location.name,
            locationUrl = location.url,
            image = image,
            episode = episode,
            url = url,
            created = created
        )
    }
}