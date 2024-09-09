package com.gkm.rickmorty.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkm.rickmorty.data.response.character.CharacterUiState
import com.gkm.rickmorty.data.response.character.UiState
import com.gkm.rickmorty.domain.useCase.character.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsCharacter @Inject constructor(
    private val useCase: CharacterUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(CharacterUiState())
    val uiState: State<CharacterUiState>
        get() = _uiState

    fun getCharacterDetail(idCharacter: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(uiState = UiState.LOADING)
            try{
                val character = withContext(Dispatchers.IO) {
                    useCase.getCharacterDetail(idCharacter)
                }
                _uiState.value = CharacterUiState(character = character, uiState = UiState.SUCCESS)
            }catch (e:Exception){
                _uiState.value = CharacterUiState(uiState = UiState.ERROR)
                Log.e("Error", e.message.toString())
            }
        }
    }
}