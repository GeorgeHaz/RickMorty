package com.gkm.rickmorty.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gkm.rickmorty.R
import com.gkm.rickmorty.components.ButtonNav
import com.gkm.rickmorty.components.MainTopBar
import com.gkm.rickmorty.navigate.RouteNav

@Composable
fun HomeView(navController: NavController) {
    Scaffold(
        topBar = {
            MainTopBar(
                Title = {
                    Text(text = "Rick and Morty",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) },
                onClickBackButton = { /*TODO*/ },
                onClickAction = { /*TODO*/ },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
            )
        }
    ) {
        BodyHome(navController = navController, paddingValues = it)
    }
}

@Composable
fun BodyHome(navController: NavController, paddingValues: PaddingValues) {
    val navigateList = listOf(
        RouteNav.Character,
        RouteNav.Episode,
        RouteNav.Location
    )
    val backImage = ImageBitmap.imageResource(R.drawable.rick_morty_home)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Image(
            bitmap = backImage,
            contentDescription = "backImage",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        navigateList.forEach {
            ButtonNav(
                tittle = it.name,
                clickNav = { navController.navigate(it.route) },
                modifier = Modifier
                    .width(200.dp)
            )
            Spacer(modifier = Modifier.size(30.dp))
        }
    }
}