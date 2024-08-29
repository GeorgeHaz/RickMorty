package com.gkm.rickmorty.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.gkm.rickmorty.R
import com.gkm.rickmorty.components.Loader
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
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchCharacter()
    }
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
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            BodyCharacter(
                navController = navController,
                parameter = characterPage
            )
        }
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
                        .padding(8.dp)
                ) {
                    navController.navigate(route = "DetailsView")
                }
            }
        }
        when (parameter.loadState.append) {
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Loader()
                    }
                }
            }

            is LoadState.Error -> {
                item {
                    Text(
                        text = stringResource(id = R.string.error_loading)
                    )
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
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onTertiary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MainImage(
                characters = characterResults,
                modifier = Modifier
                    .weight(0.8f)
            )
            MainDescription(
                characters = characterResults,
                modifier = Modifier
                    .weight(1.5f)
            )
        }

    }
}

@Composable
fun MainImage(
    characters: CharacterResults,
    modifier: Modifier = Modifier
) {
    var isLoading by remember {
        mutableStateOf(true)
    }
    val images = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(characters.image)
            .apply(
                block = fun ImageRequest.Builder.(){
                    listener(
                        onStart = {
                            isLoading = true
                        },
                        onSuccess = { _, _ ->
                            isLoading = false
                        },
                        onError = { _, _ ->
                            isLoading = false
                        }
                    )
                }
            )
            .build()
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Image(
            painter = images,
            alignment = Alignment.Center,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(
                    if (isLoading) 0f else 1f)
        )
            if(isLoading){
                Loader()
            }
    }
}


@Composable
fun MainDescription(
    characters: CharacterResults,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = characters.name,
                style = MaterialTheme.typography.titleSmall,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(
                    modifier = Modifier
                        .size(6.dp),
                    contentDescription = ""
                ) {
                    drawCircle(
                        color = when (characters.status) {
                            "Alive" -> Color.Green
                            "Dead" -> Color.Red
                            else -> Color.Gray
                        },
                        radius = size.minDimension / 2
                    )
                }
                Text(
                    text = characters.status,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Text(
                    text = " - ",
                    style = MaterialTheme.typography.labelSmall,
                )
                Text(
                    text = characters.species,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
            Spacer(
                modifier = Modifier
                    .size(4.dp)
                    .fillMaxWidth()
            )
            Box {
                Column {
                    Text(
                        text = stringResource(id = R.string.last_location),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Text(
                        text = characters.location.name,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Spacer(modifier = Modifier.size(2.dp))
            Box {
                Column {
                    Text(
                        text = stringResource(id = R.string.first_location),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
                    )
                    Text(
                        text = characters.gender,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
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
            "https://rickandmortyapi.com/api/character/avatar/227.jpeg",
            listOf(),
            "2017-11-04T18:48:46.250Z",
            "2017-11-04T18:48:46.250Z"
        ),
    ) {

    }
}