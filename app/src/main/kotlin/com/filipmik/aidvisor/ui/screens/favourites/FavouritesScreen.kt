package com.filipmik.aidvisor.ui.screens.favourites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.ui.screens.home.components.RecipeListItem

@Composable
fun FavouritesScreen(
    navController: NavController,
    viewModel: FavouritesViewModel = hiltViewModel()
) {

    with(viewModel.favouritesState) {
        Favourites.Content(
            actions = viewModel,
            favRecipes = recipes)
    }
}

object Favourites {

    interface Actions {
        fun navigateToRecipeDetail(recipe: Recipe)
    }

    @Composable
    fun Content(
        actions: Actions,
        favRecipes: List<Recipe>
    ) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(favRecipes) {index, recipe ->
                RecipeListItem(
                    recipe = recipe,
                    onItemClick = {},
                    onSaveClick = {})

                if (index < favRecipes.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}
