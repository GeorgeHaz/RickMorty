package com.gkm.rickmorty.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gkm.rickmorty.viewModel.CharacterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCharacters(viewModel: CharacterViewModel, navController: NavController) {
    var query by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    val character by viewModel.character.collectAsState()

    Scaffold(
        topBar = {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = "Buscar personaje") }
            ) {
                if (query.isNotEmpty()) {
                    val filterCharacter =
                        character.filter { it.name.contains(query, ignoreCase = true) }
                    filterCharacter.forEach {
                        Text(
                            text = it.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 10.dp, start = 10.dp)
                                .clickable {
                                    navController.navigate("DetailsView/${it.id}")
                                }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        BodyCharacter(
            navController = navController,
            paddingValues = paddingValues,
            viewModel = viewModel
        )
    }
}