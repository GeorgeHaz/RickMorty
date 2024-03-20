package com.gkm.rickmorty.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkm.rickmorty.model.CharacterResults
import com.gkm.rickmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _character = MutableStateFlow<List<CharacterResults>>(emptyList())
    val character = _character.asStateFlow()

    private val _searchCharacter =  MutableStateFlow("")
    val searchCharacter = _searchCharacter.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    init {
        fetchCharacter()
    }

    private fun fetchCharacter() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = repository.getCharacter()
                _character.value = result ?: emptyList()
            }
        }
    }
}