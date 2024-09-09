package com.gkm.rickmorty.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.useCase.character.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SearchCharacterModel @Inject constructor(
    private val useCase: CharacterUseCase
): ViewModel() {

    private val _searchCharacter = MutableStateFlow("")
    val searchCharacter = _searchCharacter.asStateFlow()


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val characters:Flow<PagingData<CharacterModel>> = searchCharacter
        .debounce(300)
        .flatMapLatest {query ->
            if(query.isEmpty()){
                flowOf(PagingData.empty())
            }else{
                useCase.invoke(query)
            }
        }
        .cachedIn(viewModelScope)

    fun searchCharacter(name:String){
        _searchCharacter.value = name
    }
}