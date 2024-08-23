package com.gkm.rickmorty.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gkm.rickmorty.data.RickAndMortyDataSource
import com.gkm.rickmorty.model.character.CharacterResults
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

    private val _filteredCharacters = MutableStateFlow<List<CharacterResults>>(emptyList())
    val filteredCharacters = _filteredCharacters

    init {
        fetchCharacter()
        searchCharacters("")
    }

    val _characterPage = Pager(PagingConfig(pageSize = 10)){
        RickAndMortyDataSource(repository)
    }.flow.cachedIn(viewModelScope)

    private fun fetchCharacter() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = repository.getCharacter()
                _character.value = result ?: emptyList()
            }
        }
    }

    private fun searchCharacters(searchText:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val filteredCharacters = character.value.filter {
                    it.name.contains(searchText, ignoreCase = true) ||
                            it.status.contains(searchText, ignoreCase = true)
                }
                _filteredCharacters.value = filteredCharacters
            }
        }
    }

    fun onSearchTextChange(characterText:String){
        _searchCharacter.value = characterText
        searchCharacters(_searchCharacter.value)
    }
}