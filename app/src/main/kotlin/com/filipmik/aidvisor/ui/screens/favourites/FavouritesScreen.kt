package com.filipmik.aidvisor.ui.screens.favourites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

        fun unfavouriteRecipe(recipe: Recipe)
    }

    @Composable
    fun Content(
        actions: Actions,
        favRecipes: List<Recipe>
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(favRecipes) {index, recipe ->
                    RecipeListItem(
                        recipe = recipe,
                        onItemClick = {

                        },
                        onSaveClick = {
                            actions.unfavouriteRecipe(it)
                        })

                    if (index < favRecipes.lastIndex) {
                        Divider(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            thickness = 1.dp
                        )
                    }
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    
                }
            ) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "FAB")
            }
        }
    }
}
