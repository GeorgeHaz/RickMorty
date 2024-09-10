package com.gkm.rickmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.gkm.rickmorty.navigate.NavManager
import com.gkm.rickmorty.ui.theme.RickMortyTheme
import com.gkm.rickmorty.viewModel.CharacterViewModel
import com.gkm.rickmorty.viewModel.DetailsCharacterViewModel
import com.gkm.rickmorty.viewModel.SearchCharacterModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel:CharacterViewModel by viewModels()
        val viewModelSearch: SearchCharacterModel by viewModels()
        val viewModelDetails: DetailsCharacterViewModel by viewModels()

        setContent {
            RickMortyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavManager(
                        viewModel = viewModel,
                        viewModelSearch = viewModelSearch,
                        viewModelDetails = viewModelDetails,
                        modifier = Modifier
                            .padding(it))
                }
            }
        }
    }
}