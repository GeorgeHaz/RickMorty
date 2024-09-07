package com.gkm.rickmorty.navigate

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gkm.rickmorty.presentation.CharacterDetailView
import com.gkm.rickmorty.presentation.CharacterView
import com.gkm.rickmorty.presentation.EpisodeView
import com.gkm.rickmorty.presentation.HomeView
import com.gkm.rickmorty.presentation.LocationView
import com.gkm.rickmorty.presentation.SearchCharacters
import com.gkm.rickmorty.viewModel.CharacterViewModel

@Composable
fun NavManager(
    viewModel: CharacterViewModel,
    modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteNav.Home.route,
        builder = {
            composable(RouteNav.Home.route) {
                HomeView(navController, modifier)
            }
            composable(RouteNav.Character.route) {
                CharacterView(navController, viewModel)
            }
            composable(RouteNav.SearchCharacters.route) {
                SearchCharacters(viewModel = viewModel, navController = navController)
            }
            composable(RouteNav.Episode.route) {
                EpisodeView(navController)
            }
            composable(RouteNav.Location.route) {
                LocationView(navController)
            }
            composable("DetailsView"){
                CharacterDetailView(navController, modifier)
            }
            /*composable("SearchBar"){
                SearchCharacters(viewModel = viewModel, navController = navController)
            }*/
        }
    )
}