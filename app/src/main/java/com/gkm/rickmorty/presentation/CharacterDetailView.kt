package com.gkm.rickmorty.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun CharacterDetailView(
    navController: NavController,
    modifier: Modifier = Modifier){
    Box(modifier = Modifier.fillMaxSize()){
        Text(
            text = "Hola, aqui van los detalles",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
    }
}