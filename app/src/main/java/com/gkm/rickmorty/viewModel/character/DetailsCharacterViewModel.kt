package com.gkm.rickmorty.viewModel.character

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkm.rickmorty.data.response.character.CharacterUiState
import com.gkm.rickmorty.data.response.UiState
import com.gkm.rickmorty.data.response.episode.EpisodeUiState
import com.gkm.rickmorty.domain.useCase.character.CharacterUseCase
import com.gkm.rickmorty.domain.useCase.episode.EpisodeUseCase
import com.gkm.rickmorty.presentation.model.episode.EpisodeDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsCharacterViewModel @Inject constructor(
    private val useCase: CharacterUseCase,
    private val episodeUseCase: EpisodeUseCase
) : ViewModel() {

    private val _uiStateEpisode:MutableStateFlow<List<EpisodeDto>> = MutableStateFlow(emptyList())
    val uiStateEpisode = _uiStateEpisode.asStateFlow()

    private val _uiState = mutableStateOf(CharacterUiState())
    val uiState: State<CharacterUiState>
        get() = _uiState



    fun getCharacterDetail(idCharacter: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(uiState = UiState.LOADING)
            try{
                withContext(Dispatchers.IO){
                    val character = useCase.getCharacterDetail(idCharacter)
                    val episode = episodeUseCase.invoke(character.episode)
                    _uiState.value = CharacterUiState(character = character, uiState = UiState.SUCCESS)
                    _uiStateEpisode.value = episode
                }

            }catch (e:Exception){
                _uiState.value = CharacterUiState(uiState = UiState.ERROR)
                Log.e("Error_Character", e.message.toString())
            }
        }
    }

    fun clearCharacterDetail() {
        _uiState.value = CharacterUiState()
    }
}