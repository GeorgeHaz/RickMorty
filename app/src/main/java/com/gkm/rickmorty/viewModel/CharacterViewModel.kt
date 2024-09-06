package com.gkm.rickmorty.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.gkm.rickmorty.data.response.character.CharacterListState
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.repository.CharacterRepository
import com.gkm.rickmorty.useCase.character.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val useCase: CharacterUseCase
) : ViewModel() {

    val characters:Flow<PagingData<CharacterModel>> = useCase.invoke()

    private var _searchResult = mutableStateOf(CharacterListState())
    val searchResult = _searchResult

    private val _searchString = MutableStateFlow("")
    val searchString = _searchString.asStateFlow()

    private var searchJob: Job? = null

    fun searchCharacter(search:String){
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if(search.length>3) delay (300)

            _searchResult.value = CharacterListState(
                characterList = characters
            )
        }
    }
}