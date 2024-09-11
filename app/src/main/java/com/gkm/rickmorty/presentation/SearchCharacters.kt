package com.gkm.rickmorty.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gkm.rickmorty.R
import com.gkm.rickmorty.components.CustomSearchBar
import com.gkm.rickmorty.components.characters.CharacterListColumn
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.viewModel.character.SearchCharacterModel

@Composable
fun SearchCharacters(
    viewModel: SearchCharacterModel,
    navController: NavController) {

    val searchName by viewModel.searchCharacter.collectAsState("")
    val characterPage = viewModel.characters.collectAsLazyPagingItems()

    Scaffold (
        topBar = {
            CustomSearchBar(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                value = searchName,
                onValueChange = {
                    viewModel.searchCharacter(it)
                },
                placeHolder = {
                    Text(text = stringResource(id = R.string.search_character))
                },
                navigateUp = { navController.navigateUp() })
        },
        containerColor = MaterialTheme.colorScheme.background
    ){
        BodySearchCharacters(
            modifier = Modifier
                .padding(it),
            characterPage = characterPage,
            navController = navController)
    }

}

@Composable
fun BodySearchCharacters(
    modifier: Modifier = Modifier,
    characterPage: LazyPagingItems<CharacterModel>,
    navController: NavController
){
    CharacterListColumn(
        modifier = modifier
            .fillMaxSize(),
        characters = characterPage,
        navController = navController
    )
}