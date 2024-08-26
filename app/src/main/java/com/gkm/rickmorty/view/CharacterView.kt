package com.gkm.rickmorty.view

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
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
    val characterPage = viewModel._characterPage.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MainTopBar(
                Title = {
                    Text(
                        text = stringResource(id = R.string.character),
                        style = MaterialTheme.typography.displaySmall
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
        BodyCharacter(
            navController = navController,
            modifier = Modifier.padding(it),
            parameter = characterPage)
    }
}

@Composable
fun BodyCharacter(
    navController: NavController,
    modifier: Modifier = Modifier,
    parameter: LazyPagingItems<CharacterResults>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(parameter.itemCount) { index ->
            val item = parameter[index]
            if (item != null) {
                CardCharacter(
                    characterResults = item,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
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
            }
            .height(150.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onTertiary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MainImage(
                characters = characterResults,
                modifier = Modifier
                    .weight(0.5f)
            )
            MainDescription(
                characters = characterResults,
                modifier = Modifier
                    .weight(0.8f))
        }

    }
}

@Composable
fun MainImage(
    characters: CharacterResults,
    modifier: Modifier = Modifier) {
    val images = rememberAsyncImagePainter(model = characters.image)
    Box(
        modifier = modifier
            .width(200.dp)
            .fillMaxHeight()
    ) {
        Image(
            painter = images,
            contentDescription = characters.name,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color.Blue)
                .fillMaxWidth()
        )
    }
}

@Composable
fun MainDescription(
    characters: CharacterResults,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = characters.name,
                style = MaterialTheme.typography.titleSmall,
            )
            Row (
                verticalAlignment = Alignment.CenterVertically){
                Canvas(
                    modifier = Modifier
                        .size(6.dp),
                    contentDescription = "") {
                    drawCircle(
                        color = if(characters.status == "Alive")
                            Color.Green else if(characters.status == "Dead")
                                Color.Red else Color.Gray,
                        radius = size.minDimension / 2
                    )
                }
                Text(
                    text = characters.status,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 4.dp))
                Text(
                    text = " - ",
                    style = MaterialTheme.typography.labelSmall,
                )
                Text(
                    text = characters.species,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
            Spacer(modifier = Modifier.size(2.dp))
            Box(){
                Column {
                    Text(
                        text = stringResource(id = R.string.last_location),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        text = characters.location.name,
                        style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(modifier = Modifier.size(2.dp))
            Box(){
                Column {
                    Text(
                        text = stringResource(id = R.string.first_location),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
                    Text(
                        text = characters.gender,
                        style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview(heightDp = 350, widthDp = 550)
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