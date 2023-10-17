package com.filipmik.aidvisor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.filipmik.aidvisor.navigation.Destination
import com.filipmik.aidvisor.ui.screens.favourites.FavouritesScreen
import com.filipmik.aidvisor.ui.screens.favourites.FavouritesViewModel
import com.filipmik.aidvisor.ui.screens.favourites.filter.FilterBottomSheet
import com.filipmik.aidvisor.ui.screens.home.HomeScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route
    ) {
        composable(Destination.Home.route) {
            HomeScreen(navController)
        }

        navigation(
            startDestination = Destination.Favourites.route,
            route = Destination.FavouritesGraph.route
        ) {
            composable(Destination.Favourites.route) {
                val favouritesViewModel = it.sharedViewModel<FavouritesViewModel>(
                    navController = navController
                )
                FavouritesScreen(navController, favouritesViewModel)
            }

            bottomSheet(Destination.FavouritesFilter.route) {
                val favouritesViewModel = it.sharedViewModel<FavouritesViewModel>(
                    navController = navController
                )
                FilterBottomSheet(favouritesViewModel)
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
