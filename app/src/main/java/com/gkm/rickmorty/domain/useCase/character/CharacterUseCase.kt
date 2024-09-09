package com.gkm.rickmorty.domain.useCase.character

import androidx.paging.PagingData
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(name:String?=null): Flow<PagingData<CharacterModel>>{
        return characterRepository.getAllCharacters(name)
    }

    suspend fun getCharacterDetail(idCharacter:Int):CharacterModel{
        return characterRepository.getCharacterDetail(idCharacter)
    }
}