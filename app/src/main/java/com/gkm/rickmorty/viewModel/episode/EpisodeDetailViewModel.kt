package com.gkm.rickmorty.viewModel.episode

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkm.rickmorty.data.response.character.CharacterUiState
import com.gkm.rickmorty.data.util.UiState
import com.gkm.rickmorty.domain.useCase.episode.EpisodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    private val useCase: EpisodeUseCase
): ViewModel() {
    private val _uiState = mutableStateOf(CharacterUiState())
    val uiState: State<CharacterUiState>
        get() = _uiState

    fun getEpisodeDetail(idEpisode:String){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(uiState = UiState.LOADING)
            try{
                val episode = withContext(Dispatchers.IO){

                }
            }catch (e:Exception) {
                _uiState.value = CharacterUiState(uiState = UiState.ERROR)
                Log.e("Error_Episode", e.message.toString())
            }
        }
    }

}