package com.gkm.rickmorty.useCase.character

import androidx.paging.PagingData
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(name:String?=null): Flow<PagingData<CharacterModel>>{
        return characterRepository.getAllCharacters(name)
    }
}