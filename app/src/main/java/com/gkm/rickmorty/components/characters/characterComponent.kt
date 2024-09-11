package com.gkm.rickmorty.components.characters

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gkm.rickmorty.R
import com.gkm.rickmorty.components.GeneralLoader
import com.gkm.rickmorty.components.Loader
import com.gkm.rickmorty.components.NotFound
import com.gkm.rickmorty.data.response.character.CharacterUiState
import com.gkm.rickmorty.data.response.episode.EpisodeUiState
import com.gkm.rickmorty.navigate.RouteNav
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.presentation.model.episode.EpisodeDto
import com.gkm.rickmorty.ui.theme.RickMortyTheme

@Composable
fun CharacterListColumn(
    modifier: Modifier = Modifier,
    characters: LazyPagingItems<CharacterModel>,
    navController: NavController,
) {
    LazyColumn(modifier)
    {
        items(characters.itemCount) {
            characters[it]?.let { characterModel ->
                CardCharacter(
                    characterModel = characterModel,
                    modifier = Modifier.padding(8.dp),
                    navController = navController
                )
            }
        }
        characters.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item {
                        GeneralLoader(modifier = Modifier.fillParentMaxSize())
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        NotFound(
                            modifier = Modifier
                                .fillMaxSize(),
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    item {
                        NotFound(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }

                loadState.hasError -> {
                    item {
                        NotFound()
                    }
                }
            }
        }

    }
}

@Composable
fun CardCharacter(
    characterModel: CharacterModel,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(route = RouteNav.DetailsCharacter.route + "/${characterModel.id}")
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MainImage(
                characters = characterModel,
                modifier = Modifier
                    .weight(0.8f)
            )
            MainDescription(
                characters = characterModel,
                modifier = Modifier
                    .weight(1.5f)
            )
        }

    }
}

@Composable
fun MainImage(
    modifier: Modifier = Modifier,
    characters: CharacterModel = CharacterModel(),
) {
    var isLoading by remember {
        mutableStateOf(true)
    }
    val images = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(characters.image)
            .apply(
                block = fun ImageRequest.Builder.() {
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
        contentAlignment = Alignment.Center,
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
                    if (isLoading) 0f else 1f
                )
        )
        if (isLoading) {
            Loader()
        }
    }
}


@Composable
fun MainDescription(
    characters: CharacterModel,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp)
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
                    .size(2.dp)
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
                        text = characters.locationName,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailTopBar(
    navController: NavController,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    MediumTopAppBar(
        modifier = modifier,
        title = {
            text()
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }
        })
}

@Composable
fun CharacterDetailBody(
    modifier: Modifier = Modifier,
    detailCharacter: CharacterUiState,
    episodeDetail:List<EpisodeDto>
) {
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.detail_character),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp, top = 10.dp),
            )
            Column(modifier = Modifier.padding(bottom = 10.dp)) {
                BoxCharacterDetail(
                    textTittle = stringResource(id = R.string.specie),
                    text = detailCharacter.character.species
                )
                BoxCharacterDetail(
                    textTittle = stringResource(id = R.string.type),
                    text = detailCharacter.character.type
                )
                BoxCharacterDetail(
                    textTittle = stringResource(id = R.string.gender),
                    text = detailCharacter.character.gender
                )
                BoxCharacterDetail(
                    textTittle = stringResource(id = R.string.origin),
                    text = detailCharacter.character.originName
                )
                BoxCharacterDetail(
                    textTittle = stringResource(id = R.string.location),
                    text = detailCharacter.character.locationName
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.episode_list) + " (${detailCharacter.character.episode.size})",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp),
            )
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp)
            ) {
                items( episodeDetail.size) {

                    BoxCharacterDetail(
                        textTittle = "",
                        text = episodeDetail[it].name
                    )
                }
            }
        }
    }
}

@Composable
fun BoxCharacterDetail(
    modifier: Modifier = Modifier,
    textTittle: String,
    text: String,
) {
    Box(
        modifier = modifier
            .padding(start = 40.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = textTittle,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BoxPreview() {
    RickMortyTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = stringResource(id = R.string.detail_character),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            BoxCharacterDetail(
                textTittle = stringResource(id = R.string.specie),
                text = "Human"
            )
            BoxCharacterDetail(
                textTittle = stringResource(id = R.string.type),
                text = "Animal"
            )
            BoxCharacterDetail(
                textTittle = stringResource(id = R.string.gender),
                text = "Male"
            )
            BoxCharacterDetail(
                textTittle = stringResource(id = R.string.origin),
                text = "Planet black"
            )
            BoxCharacterDetail(
                textTittle = stringResource(id = R.string.location),
                text = "Terra"
            )
        }
    }
}

//https://github.com/JoyceQuerubino/Rick-and-Morty