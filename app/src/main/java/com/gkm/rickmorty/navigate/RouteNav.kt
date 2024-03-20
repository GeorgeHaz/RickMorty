package com.gkm.rickmorty.navigate

sealed class RouteNav(
    val route: String,
    val name: String,
) {
    data object Home : RouteNav(route = "Home_Screen", name = "Home")
    data object Character : RouteNav(route = "Character_Screen", name = "Character")
    data object Episode : RouteNav(route = "Episode_Screen", name = "Episode")
    data object Location : RouteNav(route = "Location_Screen", name = "Location")
}