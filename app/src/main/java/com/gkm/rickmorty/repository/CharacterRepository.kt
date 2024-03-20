package com.gkm.rickmorty.repository

import com.gkm.rickmorty.data.ApiRickMorty
import com.gkm.rickmorty.model.CharacterResults
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val apiRickMorty: ApiRickMorty) {

    suspend fun getCharacter():List<CharacterResults>?{
        val response = apiRickMorty.getCharacter()
        if(response.isSuccessful){
            return response.body()?.results
        }
        return null
    }
}