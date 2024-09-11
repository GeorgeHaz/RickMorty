package com.gkm.rickmorty.navigate

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gkm.rickmorty.presentation.CharacterDetailView
import com.gkm.rickmorty.presentation.CharacterView
import com.gkm.rickmorty.presentation.EpisodeView
import com.gkm.rickmorty.presentation.HomeView
import com.gkm.rickmorty.presentation.LocationView
import com.gkm.rickmorty.presentation.SearchCharacters
import com.gkm.rickmorty.viewModel.character.CharacterViewModel
import com.gkm.rickmorty.viewModel.character.DetailsCharacterViewModel
import com.gkm.rickmorty.viewModel.character.SearchCharacterModel

@Composable
fun NavManager(
    viewModel: CharacterViewModel,
    viewModelSearch: SearchCharacterModel,
    viewModelDetails: DetailsCharacterViewModel,
    modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteNav.Home.route,
        builder = {
            composable(
                route = RouteNav.Home.route)
            {
                HomeView(
                    navController = navController,
                    modifier = modifier)
            }
            composable(
                route = RouteNav.Character.route)
            {
                CharacterView(
                    navController = navController,
                    viewModel = viewModel)
            }
            composable(
                route = RouteNav.SearchCharacters.route)
            {
                SearchCharacters(
                    viewModel = viewModelSearch,
                    navController = navController)
            }
            composable(
                route = RouteNav.Episode.route)
            {
                EpisodeView(
                    navController = navController)
            }
            composable(
                route = RouteNav.Location.route)
            {
                LocationView(
                    navController = navController)
            }
            composable(
                route = "${RouteNav.DetailsCharacter.route}/{id}",
                arguments = listOf(navArgument(
                    name = "id") {
                    type = NavType.IntType }
                )
            ){
                val id = it.arguments?.getInt("id") ?: 0
                CharacterDetailView(
                    viewModel = viewModelDetails,
                    navController = navController,
                    modifier = modifier,
                    id = id)
            }
        }
    )
}