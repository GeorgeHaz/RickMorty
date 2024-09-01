package com.gkm.rickmorty.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gkm.rickmorty.data.ApiRickMorty
import com.gkm.rickmorty.data.RickAndMortyDataSource
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.util.Constans.Companion.MAX_ITEMS
import com.gkm.rickmorty.util.Constans.Companion.PREFETCH_ITEMS
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val apiRickMorty: ApiRickMorty) {

    fun getAllCharacters(): Flow<PagingData<CharacterModel>>{
        return Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                RickAndMortyDataSource(apiRickMorty)
            }).flow
    }
}