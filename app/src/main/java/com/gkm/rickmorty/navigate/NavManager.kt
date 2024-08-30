package com.gkm.rickmorty.navigate

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gkm.rickmorty.view.CharacterDetailView
import com.gkm.rickmorty.view.CharacterView
import com.gkm.rickmorty.view.EpisodeView
import com.gkm.rickmorty.view.HomeView
import com.gkm.rickmorty.view.LocationView
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