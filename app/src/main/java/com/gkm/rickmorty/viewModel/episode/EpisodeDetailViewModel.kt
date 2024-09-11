package com.gkm.rickmorty.viewModel.episode

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkm.rickmorty.data.response.UiState
import com.gkm.rickmorty.data.response.episode.EpisodeUiState
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
    private val _uiState = mutableStateOf(EpisodeUiState())
    val uiState: State<EpisodeUiState>
        get() = _uiState

    fun getEpisodeDetail(idEpisode:String){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(uiState = UiState.LOADING)
            try{
                val episode = withContext(Dispatchers.IO){
                    useCase.getEpisodeDetail(idEpisode)
                }
            }catch (e:Exception) {
                _uiState.value = EpisodeUiState(uiState = UiState.ERROR)
                Log.e("Error_Episode", e.message.toString())
            }
        }
    }

}