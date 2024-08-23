package com.gkm.rickmorty.view

import android.graphics.Paint.Style
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.gkm.rickmorty.R
import com.gkm.rickmorty.components.MainTopBar
import com.gkm.rickmorty.model.character.CharacterResults
import com.gkm.rickmorty.model.character.Location
import com.gkm.rickmorty.model.character.Origin
import com.gkm.rickmorty.viewModel.CharacterViewModel

@Composable
fun CharacterView(
    navController: NavController,
    viewModel: CharacterViewModel
) {

    //val character by viewModel.character.collectAsState()
    val characterPage = viewModel._characterPage.collectAsLazyPagingItems()

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
        BodyCharacter(navController = navController, paddingValues = it, characterPage)
    }
}

@Composable
fun BodyCharacter(
    navController: NavController,
    paddingValues: PaddingValues,
    parameter: LazyPagingItems<CharacterResults>
    //parameter:List<CharacterResults>,
) {

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        items(parameter.itemCount) { index ->
            val item = parameter[index]
            if (item != null) {
                CardCharacter(
                    characterResults = item,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    navController.navigate(route = "DetailsView")
                }
            }
        }
    }
}

@Composable
fun CardCharacter(
    characterResults: CharacterResults,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onTertiary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(maxHeight = 150.dp)
        ) {
            MainImage(
                characters = characterResults,
                modifier = Modifier
                    .sizeIn(maxWidth = 100.dp)
            )
            MainDescription(characters = characterResults)
        }

    }
}

@Composable
fun MainImage(characters: CharacterResults, modifier: Modifier = Modifier) {
    val images = rememberAsyncImagePainter(model = characters.image)
    Box(
        modifier = modifier
    ) {
        Image(
            painter = images,
            contentDescription = characters.name,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color.Blue)
        )
    }
}

@Composable
fun MainDescription(
    characters: CharacterResults,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxHeight(),
    ) {
        Text(
            text = characters.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 0.dp)
        )
        Row (
            verticalAlignment = Alignment.CenterVertically){
            Canvas(
                modifier = Modifier
                    .size(6.dp),
                contentDescription = "") {
                drawCircle(
                    color = if(characters.status == "Alive") Color.Green else if(characters.status == "Dead") Color.Red else Color.Gray,
                    radius = size.minDimension / 2
                )
            }
            Text(
                text = characters.status,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp))
            Text(
                text = " - ",
            )
            Text(
                text = characters.species,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                )
        }
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = stringResource(id = R.string.last_location),
            color = MaterialTheme.colorScheme.outline,
            fontSize = 10.sp
        )
        Text(text = characters.location.name, fontWeight = FontWeight.Medium, fontSize = 12.sp)
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = stringResource(id = R.string.first_location),
            color = MaterialTheme.colorScheme.outline,
            fontSize = 10.sp
        )
        Text(text = characters.gender, fontWeight = FontWeight.Medium, fontSize = 12.sp)
    }
}

@Preview
@Composable
fun CardPreview() {
    CardCharacter(
        characterResults = CharacterResults(
            1,
            "Rick Sanchez",
            "Alive",
            "Human",
            "",
            "Male",
            Origin("Earth", "Planet"),
            Location("Earth", "Planet"),
            "",
            listOf(),
            "2017-11-04T18:48:46.250Z",
            "2017-11-04T18:48:46.250Z"
        ),
    ) {
    }
}