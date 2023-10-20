package com.filipmik.aidvisor.navigation

sealed class Destination(
    val route: String,
) {
    object Home : Destination(route = "home_screen")

    object FavouritesGraph : Destination(route = "favourites_graph")
    object Favourites : Destination(route = "favourites_screen")
    object FavouritesFilter : Destination(route = "favourites_filter")
}
