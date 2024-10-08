package com.gkm.rickmorty.navigate

import androidx.annotation.StringRes
import com.gkm.rickmorty.R

sealed class RouteNav(
    val route: String,
    @StringRes val name: Int,
) {
    data object Home : RouteNav(route = "Home_Screen", name = R.string.home)
    data object Character : RouteNav(route = "Character_Screen", name = R.string.character)
    data object SearchCharacters : RouteNav(route = "SearchCharacters_Screen", name = R.string.search_character)
    data object DetailsCharacter : RouteNav(route = "DetailsCharacter_Screen", name = R.string.details_character)
    data object Episode : RouteNav(route = "Episode_Screen", name = R.string.episode)
    data object Location : RouteNav(route = "Location_Screen", name = R.string.location)
}