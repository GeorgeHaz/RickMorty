package com.gkm.rickmorty.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    repository: CharacterRepository,
) : ViewModel() {

    val characters:Flow<PagingData<CharacterModel>> = repository.getAllCharacters()

}