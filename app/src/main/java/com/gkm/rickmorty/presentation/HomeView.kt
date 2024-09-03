package com.gkm.rickmorty.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gkm.rickmorty.R
import com.gkm.rickmorty.components.ButtonNav
import com.gkm.rickmorty.navigate.RouteNav
import com.gkm.rickmorty.ui.theme.RickMortyTheme

@Composable
fun HomeView(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    BodyHome(
        navController = navController,
        modifier = modifier
    )
}

@Composable
fun BodyHome(
    navController: NavController,
    modifier: Modifier
) {
    val navigateList = listOf(
        RouteNav.Character,
        RouteNav.Episode,
        RouteNav.Location
    )
    val backImage = ImageBitmap.imageResource(R.drawable.rick_morty_home)

    Image(
        bitmap = backImage,
        contentDescription = "backImage",
        modifier = modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.9f
    )
    Column (modifier = modifier.fillMaxSize()){
        Box(contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ){
            Image(
                painter = painterResource(id = R.drawable.rickmorty),
                contentDescription = "logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                navigateList.forEach {
                    ButtonNav(
                        tittle = stringResource(id = it.name),
                        clickNav = {
                            navController.navigate(it.route)
                        },
                        modifier = Modifier
                            .width(115.dp),
                        color = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    RickMortyTheme (darkTheme = true){
        HomeView(navController = rememberNavController())
    }
}