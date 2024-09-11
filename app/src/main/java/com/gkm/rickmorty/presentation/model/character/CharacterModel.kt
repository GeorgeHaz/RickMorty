package com.gkm.rickmorty.presentation.model.character

data class CharacterModel(
    val id:Int = 0,
    val name:String = "",
    val status:String = "",
    val species:String = "",
    val type:String = "None",
    val gender:String = "",
    val originName:String = "",
    val originUrl:String = "",
    val locationName:String = "",
    val locationUrl:String = "",
    val image:String = "",
    val episode:List<String> = emptyList(),
    val url:String = "",
    val created:String = ""
)
