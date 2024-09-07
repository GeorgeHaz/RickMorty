package com.gkm.rickmorty.presentation.model.character

import androidx.lifecycle.ViewModel
import com.gkm.rickmorty.useCase.character.CharacterUseCase
import dagger.hilt.android.scopes.ViewScoped
import javax.inject.Inject

@ViewScoped
class SearchCharacterModel @Inject constructor(
    private val useCase: CharacterUseCase
): ViewModel() {

}