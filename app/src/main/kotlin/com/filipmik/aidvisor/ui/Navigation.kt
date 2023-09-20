package com.filipmik.aidvisor.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.filipmik.aidvisor.navigation.Destination
import com.filipmik.aidvisor.ui.screens.detail.DetailScreen
import com.filipmik.aidvisor.ui.screens.home.HomeScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route,
    ) {

        composable(Destination.Home.route) {
            HomeScreen(navController)
        }

        composable(Destination.Detail.route) {
            DetailScreen(navController)
        }
    }
}
