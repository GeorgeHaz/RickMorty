package com.gkm.rickmorty.presentation.model.episode

data class EpisodeDto(
    val id:Int = 0,
    val name:String = "",
    val airDate:String = "",
    val episode:String = "",
    val characters:List<String> = emptyList(),
    val url:String = "",
    val created:String  = ""
)
