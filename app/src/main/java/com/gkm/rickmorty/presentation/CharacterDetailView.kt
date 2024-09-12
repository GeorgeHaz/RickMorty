package com.gkm.rickmorty.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gkm.rickmorty.components.GeneralLoader
import com.gkm.rickmorty.components.characters.CharacterDetailBody
import com.gkm.rickmorty.components.characters.CharacterDetailTopBar
import com.gkm.rickmorty.data.response.character.CharacterUiState
import com.gkm.rickmorty.data.response.episode.EpisodeUiState
import com.gkm.rickmorty.data.util.UiState
import com.gkm.rickmorty.viewModel.character.DetailsCharacterViewModel

@Composable
fun CharacterDetailView(
    modifier: Modifier = Modifier,
    viewModel: DetailsCharacterViewModel = hiltViewModel(),
    navController: NavController,
    id: Int,
) {
    val detail = viewModel.uiState.value
    val episodeDetail = viewModel.stateEpisode.value

    LaunchedEffect(key1 = Unit) {
        viewModel.getCharacterDetail(id)
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            viewModel.clearCharacterDetail()
        }
    }
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            CharacterDetailTopBar(
                modifier = Modifier
                    .fillMaxWidth(),
                navController = navController,
                text = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = detail.character.name,
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                })
        }
    ) {
        BodyCharacterDetails(
            modifier = Modifier
                .padding(it),
            detail = detail,
            episodeDetail = episodeDetail
        )
    }
}

@Composable
fun BodyCharacterDetails(
    modifier: Modifier = Modifier,
    detail: CharacterUiState,
    episodeDetail: EpisodeUiState
) {
    if (detail.uiState == UiState.SUCCESS) {
        Column(
            modifier = modifier.fillMaxSize()
        )
        {
            ImageCharacter(
                detail = detail
            )
            CharacterDetail(
                detail = detail,
                episodeDetail = episodeDetail
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            GeneralLoader()
        }
    }

}

@Composable
fun ImageCharacter(
    detail: CharacterUiState,
    modifier: Modifier = Modifier
) {
    val imageCharacter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(detail.character.image)
            .build()
    )
    val colorLive = when (detail.character.status) {
        "Alive" -> Color.Green
        "Dead" -> Color.Red
        else -> Color.Gray
    }
    val sizeCharacter = when (detail.character.status) {
        "unknown" -> 200.dp
        else -> 220.dp
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .height(sizeCharacter)
    )
    {
        Image(
            painter = imageCharacter,
            contentDescription = detail.character.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(5.dp, colorLive, CircleShape)
                .align(Alignment.Center)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(10.dp))
                .background(colorLive)
                .padding(4.dp)
        ) {
            Text(
                text = detail.character.status,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                letterSpacing = 0.2.em
            )
        }
    }

}

@Composable
fun CharacterDetail(
    detail: CharacterUiState,
    episodeDetail: EpisodeUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        CharacterDetailBody(
            detailCharacter = detail,
            episodeDetail = episodeDetail
        )
    }
}