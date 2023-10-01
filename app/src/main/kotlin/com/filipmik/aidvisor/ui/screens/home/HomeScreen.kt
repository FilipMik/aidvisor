package com.filipmik.aidvisor.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.ui.screens.home.components.RecipeListItem

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    with(viewModel.homeState) {
        Home.Content(
            viewModel,
            apiCompletionState,
            ingredients,
            recipes
        )
    }
}

object Home {

    interface Actions {
        fun navigateToDetailScreen()

        fun onRetry()

        fun setIngredients(ingredients: String)

        fun fetchRecipes()
    }

    @Composable
    fun Content(
        actions: Actions,
        apiCompletionState: ScreenState,
        ingredients: String,
        recipes: List<Recipe>
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.padding(20.dp))
                IngredientsInput(apiCompletionState, ingredients, actions)
            }

            if (recipes.isNotEmpty()) {
                Column (
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth()
                ) {
                    RecipesList(recipes)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun IngredientsInput(
        apiCompletionState: ScreenState,
        ingredients: String,
        actions: Actions
    ) {
        Text(text = "What's in your fridge?")

        Spacer(modifier = Modifier.padding(4.dp))

        OutlinedTextField(
            modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp),
            value = ingredients,
            onValueChange = { actions.setIngredients(it) },
            label = { Text("Ingredients") }
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Row {
            Spacer(modifier = Modifier.weight(2f))

            Button(
                modifier = Modifier.weight(5f),
                contentPadding = PaddingValues(4.dp),
                onClick = { actions.fetchRecipes() },
                enabled = apiCompletionState != ScreenState.LOADING
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(4.dp)
                ){
                    if (apiCompletionState == ScreenState.LOADING) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(text = "Give me recipes!")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(2f))
        }
    }

    @Composable
    fun RecipesList(
        recipes: List<Recipe>
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(recipes) { index, recipe ->
                RecipeListItem(recipe = recipe, onItemClick = {})
                if (index < recipes.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}
