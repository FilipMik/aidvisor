package com.filipmik.aidvisor.navigation

sealed class Destination(
    val route: String,
) {
    object Home : Destination(route = "home_screen")
    object Detail : Destination(route = "detail_screen")
}
