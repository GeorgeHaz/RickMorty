package com.gkm.rickmorty.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.useCase.character.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    useCase: CharacterUseCase
) : ViewModel() {

    val characters:Flow<PagingData<CharacterModel>> = useCase.invoke()
}