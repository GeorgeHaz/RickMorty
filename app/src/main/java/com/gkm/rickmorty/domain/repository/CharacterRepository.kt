package com.gkm.rickmorty.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gkm.rickmorty.data.network.ApiRickMorty
import com.gkm.rickmorty.data.pagging.RickAndMortyDataSource
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.data.util.Constans.Companion.MAX_ITEMS
import com.gkm.rickmorty.data.util.Constans.Companion.PREFETCH_ITEMS
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val apiRickMorty: ApiRickMorty) {

    fun getAllCharacters(name:String?=null): Flow<PagingData<CharacterModel>>{
        return Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                RickAndMortyDataSource(apiRickMorty, name)
            }).flow
    }

    suspend fun getCharacterDetail(idCharacter:Int):CharacterModel{
        return apiRickMorty.getCharacterDetail(idCharacter).toPresentation()
    }
}