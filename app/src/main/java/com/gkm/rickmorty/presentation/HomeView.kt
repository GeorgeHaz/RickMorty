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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gkm.rickmorty.R
import com.gkm.rickmorty.components.ButtonNav
import com.gkm.rickmorty.navigate.RouteNav

@Composable
fun HomeView(
    navController: NavController,
    modifier: Modifier = Modifier) {
    BodyHome(
        navController = navController,
        modifier = modifier)
}

@Composable
fun BodyHome(navController: NavController, modifier: Modifier) {
    val navigateList = listOf(
        RouteNav.Character,
        RouteNav.Episode,
        RouteNav.Location
    )
    val backImage = ImageBitmap.imageResource(R.drawable.rick_morty_home)
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            bitmap = backImage,
            contentDescription = "backImage",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    Image(
        painter = painterResource(id = R.drawable.rickmorty),
        contentDescription = "logo",
        alignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 25.dp, horizontal = 5.dp))
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        navigateList.forEach {
            ButtonNav(
                tittle = stringResource(id =it.name),
                clickNav = {
                    navController.navigate(it.route) },
                modifier = Modifier
                    .width(150.dp)
            )
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}