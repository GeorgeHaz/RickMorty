package com.gkm.rickmorty.viewModel.character

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkm.rickmorty.data.response.ResponseUiState
import com.gkm.rickmorty.data.response.episode.EpisodeUiState
import com.gkm.rickmorty.data.util.UiState
import com.gkm.rickmorty.domain.useCase.character.CharacterUseCase
import com.gkm.rickmorty.domain.useCase.episode.EpisodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsCharacterViewModel @Inject constructor(
    private val useCase: CharacterUseCase,
    private val episodeUseCase: EpisodeUseCase,
) : ViewModel() {

    private val _uiState = mutableStateOf(ResponseUiState())
    val uiState: State<ResponseUiState>
        get() = _uiState

    private val _episodeUiState = mutableStateOf(EpisodeUiState())
    val episodeUiState: State<EpisodeUiState>
        get() = _episodeUiState

    fun getCharacterDetail(idCharacter: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(uiState = UiState.LOADING)
            try {
                val character = useCase.getCharacterDetail(idCharacter)
                _uiState.value = ResponseUiState(character = character, uiState = UiState.SUCCESS)

                getEpisodeDetail(character.episode)

            } catch (e: Exception) {
                _uiState.value = ResponseUiState(uiState = UiState.ERROR)
                Log.e("Error_Character", e.message.toString())
            }
        }
    }

    private fun extractEpisodeId(episodeUrl: String): String {
        val episodeId = episodeUrl.split("/")
        return episodeId.last()
    }

    private fun getEpisodeDetail(episodeUrls: List<String>){
        viewModelScope.launch {
            try{
                val episodeIds = episodeUrls.map {extractEpisodeId(it)}
                val episode = withContext(Dispatchers.IO){
                    episodeUseCase.invoke(episodeIds)
                }
                Log.e("Error_Epis",episodeIds.toString())
                _episodeUiState.value = EpisodeUiState(episode = episode, uiState = UiState.SUCCESS)
            }catch (e:Exception){
                _episodeUiState.value = EpisodeUiState(uiState = UiState.ERROR)
                Log.e("Error_Episode",e.message.toString())

            }
        }
    }

    fun clearCharacterDetail() {
        _uiState.value = ResponseUiState()
        _episodeUiState.value = EpisodeUiState()
    }
}