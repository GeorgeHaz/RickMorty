package com.gkm.rickmorty.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.gkm.rickmorty.components.MainTopBar
import com.gkm.rickmorty.model.CharacterResults
import com.gkm.rickmorty.viewModel.CharacterViewModel

@Composable
fun CharacterView(navController: NavController, viewModel: CharacterViewModel) {

    val character by viewModel.character.collectAsState()

    Scaffold(
        topBar = {
            MainTopBar(
                Title = {
                    Text(
                        text = "Personajes",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                showBackButton = true,
                onClickBackButton = {
                    navController.popBackStack()
                },
                showSearchButton = true,
                onClickAction = {
                    navController.navigate("SearchBar")
                },
                showImage = true,
                modifier = Modifier
            )
        }
    ) {
        BodyCharacter(navController = navController, paddingValues = it, character)
    }
}

@Composable
fun BodyCharacter(
    navController: NavController,
    paddingValues: PaddingValues,
    parameter:List<CharacterResults>,
) {

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        items(parameter) { item ->
            CardCharacter(
                characterResults = item
            ) {
                navController.navigate(route = "DetailsView")
            }
        }
    }
}

@Composable
fun CardCharacter(characterResults: CharacterResults, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(5.dp)
            .shadow(40.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onTertiary)
    ) {
        Row {
            MainImage(characters = characterResults, modifier = Modifier.weight(0.8f))
            MainDescription(characters = characterResults, modifier = Modifier.weight(1.2f))
        }

    }
}

@Composable
fun MainImage(characters: CharacterResults, modifier: Modifier) {
    val images = rememberAsyncImagePainter(model = characters.image)

    Image(
        painter = images,
        contentDescription = characters.name,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .height(130.dp)
    )
}

@Composable
fun MainDescription(
    characters: CharacterResults,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Text(
            text = characters.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(text = characters.status, fontWeight = FontWeight.Medium, fontSize = 12.sp)
        Spacer(modifier = Modifier.size(2.dp))
        Text(text = "Fecha de aparicion:", color = MaterialTheme.colorScheme.outline,fontSize = 10.sp)
        Text(text = characters.created, fontWeight = FontWeight.Medium,fontSize = 12.sp)
        Spacer(modifier = Modifier.size(2.dp))
        Text(text = "Genero:", color = MaterialTheme.colorScheme.outline, fontSize = 10.sp)
        Text(text = characters.gender, fontWeight = FontWeight.Medium, fontSize = 12.sp)
    }
}