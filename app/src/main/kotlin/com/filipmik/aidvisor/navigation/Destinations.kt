package com.filipmik.aidvisor.navigation

sealed class Destination(
    val route: String,
) {
    object Home : Destination(route = "home_screen")
    object Favourites : Destination(route = "favourites_screen")
}
