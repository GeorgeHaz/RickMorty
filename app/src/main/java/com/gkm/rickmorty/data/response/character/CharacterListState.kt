package com.gkm.rickmorty.data.response.character

import androidx.paging.PagingData
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import kotlinx.coroutines.flow.Flow

data class CharacterListState(
    var isLoading:Boolean = false,
    val characterList: Flow<PagingData<CharacterModel>>? = null,
    val errorMessage:String = ""
)
