package com.filipmik.aidvisor.ui.screens.favourites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.model.RecipeFilter
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class FavouritesState {

    var recipes: List<Recipe> by mutableStateOf(listOf())

    var recipeFilter: RecipeFilter by mutableStateOf(RecipeFilter())

    var error: String by mutableStateOf("")

}
