package com.gkm.rickmorty.viewModel.character

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkm.rickmorty.data.response.character.CharacterUiState
import com.gkm.rickmorty.data.response.episode.EpisodeUiState
import com.gkm.rickmorty.data.util.UiState
import com.gkm.rickmorty.domain.useCase.character.CharacterUseCase
import com.gkm.rickmorty.domain.useCase.episode.EpisodeUseCase
import com.gkm.rickmorty.presentation.model.episode.EpisodeDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsCharacterViewModel @Inject constructor(
    private val useCase: CharacterUseCase,
    private val episodeUseCase: EpisodeUseCase
) : ViewModel() {

    private val _stateEpisode = mutableStateOf(EpisodeUiState())
    val stateEpisode: State<EpisodeUiState>
        get() = _stateEpisode

    private val _uiState = mutableStateOf(CharacterUiState())
    val uiState: State<CharacterUiState>
        get() = _uiState



    fun getCharacterDetail(idCharacter: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(uiState = UiState.LOADING)
            try{
                    val character = withContext(Dispatchers.IO){
                        useCase.getCharacterDetail(idCharacter)
                    }
                    _uiState.value = CharacterUiState(character = character, uiState = UiState.SUCCESS)
                    getEpisodeDetail(character.episode)
            }catch (e:Exception){
                _uiState.value = CharacterUiState(uiState = UiState.ERROR)
                Log.e("Error_Character", e.message.toString())
            }
        }
    }

    private fun getEpisodeDetail(idEpisode: List<String>){
        viewModelScope.launch {
            val episode = withContext(Dispatchers.IO){
                episodeUseCase.invoke(idEpisode)
            }
            _stateEpisode.value = EpisodeUiState(episode = episode,uiState = UiState.SUCCESS)
        }
    }

    fun clearCharacterDetail() {
        _uiState.value = CharacterUiState()
        _stateEpisode.value = EpisodeUiState()
    }
}