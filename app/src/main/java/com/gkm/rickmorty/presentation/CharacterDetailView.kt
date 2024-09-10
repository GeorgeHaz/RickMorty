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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gkm.rickmorty.components.characters.CharacterDetailTopBar
import com.gkm.rickmorty.presentation.model.character.CharacterModel
import com.gkm.rickmorty.ui.theme.RickMortyTheme
import com.gkm.rickmorty.viewModel.DetailsCharacterViewModel

@Composable
fun CharacterDetailView(
    modifier: Modifier = Modifier,
    viewModel: DetailsCharacterViewModel = hiltViewModel(),
    navController: NavController,
    id: Int,
) {
    val result = viewModel.uiState.value.character
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
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                text = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = result.name,
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                })
        }
    ) {
        BodyCharacterDetails(
            modifier = Modifier
                .padding(it),
            result = result
        )
    }
}

@Composable
fun BodyCharacterDetails(
    modifier: Modifier = Modifier,
    result: CharacterModel,
) {
    Column(
        modifier = modifier.fillMaxSize()
    )
    {
        ImageCharacter(
            result = result
        )
    }
}

@Composable
fun ImageCharacter(
    result: CharacterModel,
    modifier: Modifier = Modifier
) {
    val imageCharacter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(result.image)
            .build()
    )
    val colorLive = when(result.status){
        "Alive" -> Color.Green
        "Dead" -> Color.Red
        else -> Color.Gray
    }
    val sizeCharacter = when(result.status){
        "unknown" -> 200.dp
        else -> 220.dp
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(sizeCharacter)
        ){
        Image(
            painter = imageCharacter,
            contentDescription = result.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
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
        ){
            Text(
                text = result.status,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                letterSpacing = 0.2.em
                )
        }
    }
}

@Preview
@Composable
fun PreviewCharacterDetailView() {
    RickMortyTheme {
        ImageCharacter(result = CharacterModel(status = "unknown"))
    }
}