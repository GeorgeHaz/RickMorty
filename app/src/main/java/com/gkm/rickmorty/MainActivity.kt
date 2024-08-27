package com.gkm.rickmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.gkm.rickmorty.navigate.NavManager
import com.gkm.rickmorty.ui.theme.RickMortyTheme
import com.gkm.rickmorty.viewModel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel:CharacterViewModel by viewModels()

        setContent {
            RickMortyTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavManager(
                        viewModel = viewModel,
                        modifier = Modifier
                            .padding(it))
                }
            }
        }
    }
}