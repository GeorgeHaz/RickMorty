package com.gkm.rickmorty.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.gkm.rickmorty.R
import com.gkm.rickmorty.components.CustomSearchBar
import com.gkm.rickmorty.viewModel.CharacterViewModel

@Composable
fun SearchCharacters(
    viewModel: CharacterViewModel,
    navController: NavController) {
    var value by remember {
        mutableStateOf("")
    }
    Scaffold (
        topBar = {
            CustomSearchBar(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                value = value,
                onValueChange = {
                    value = it
                },
                placeHolder = {
                    Text(text = stringResource(id = R.string.search_character))
                },
                navigateUp = { navController.navigateUp() })
        },
        containerColor = MaterialTheme.colorScheme.background
    ){
        BodySearchCharacters(modifier = Modifier.padding(it))
    }

}

@Composable
fun BodySearchCharacters(
    modifier: Modifier = Modifier
){

}