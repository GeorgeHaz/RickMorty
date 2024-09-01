package com.gkm.rickmorty.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gkm.rickmorty.data.RickAndMortyDataSource
import com.gkm.rickmorty.data.response.character.CharacterResults
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    repository: CharacterRepository,
) : ViewModel() {

    val characters:Flow<PagingData<CharacterModel>> = repository.getAllCharacters()

}

//private val _character = MutableStateFlow<List<CharacterResults>>(emptyList())
//    val character = _character.asStateFlow()
//
//    val _characterPage = Pager(PagingConfig(pageSize = 10)){
//        RickAndMortyDataSource(repository)
//    }.flow.cachedIn(viewModelScope)
//
//    fun fetchCharacter() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                val result = repository.getCharacter()
//                _character.value = result ?: emptyList()
//            }
//        }
//    }