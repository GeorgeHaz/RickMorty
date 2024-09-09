package com.gkm.rickmorty.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gkm.rickmorty.R
import com.gkm.rickmorty.components.GeneralLoader
import com.gkm.rickmorty.components.MainTopBar
import com.gkm.rickmorty.components.NotInternetLoader
import com.gkm.rickmorty.components.characters.CharacterListColumn
import com.gkm.rickmorty.navigate.RouteNav
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.viewModel.CharacterViewModel

@Composable
fun CharacterView(
    navController: NavController,
    viewModel: CharacterViewModel = hiltViewModel(),
) {
    val characterPage = viewModel.characters.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier,
        topBar = {
            HeadCharacter(
                navController = navController)},
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        when {
            //start charge
            characterPage.loadState.refresh is LoadState.Loading && characterPage.itemCount == 0 -> {
                GeneralLoader(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                )
            }

            characterPage.loadState.hasError -> {
                NotInternetLoader(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                )
            }

            else -> {
                BodyCharacter(
                    modifier = Modifier.padding(it),
                    characterPage = characterPage,
                    navController = navController
                )
            }
        }

    }
}

@Composable
fun HeadCharacter(
    navController: NavController,
) {

    MainTopBar(
        title = {
            Text(
                text = stringResource(id = R.string.character),
                style = MaterialTheme.typography.displaySmall
            )
        },
        showButton = true,
        button = Icons.AutoMirrored.Filled.ArrowBack,
        onClickBackButton = {
            navController.popBackStack()
        },
        showImage = true,
        showSearch = true,
        onClickAction = {
            navController.navigate(RouteNav.SearchCharacters.route)
        }
    )
}

@Composable
fun BodyCharacter(
    modifier: Modifier = Modifier,
    characterPage: LazyPagingItems<CharacterModel>,
    navController: NavController
) {

    CharacterListColumn(
        modifier = modifier
            .fillMaxSize(),
        characters = characterPage,
        onClick = {
            navController.navigate(route = "DetailsView")
        },
    )
}





