package com.gkm.rickmorty.navigate

import androidx.annotation.StringRes
import com.gkm.rickmorty.R

sealed class RouteNav(
    val route: String,
    @StringRes val name: Int,
) {
    data object Home : RouteNav(route = "Home_Screen", name = R.string.home)
    data object Character : RouteNav(route = "Character_Screen", name = R.string.character)
    data object Episode : RouteNav(route = "Episode_Screen", name = R.string.episode)
    data object Location : RouteNav(route = "Location_Screen", name = R.string.location)
}