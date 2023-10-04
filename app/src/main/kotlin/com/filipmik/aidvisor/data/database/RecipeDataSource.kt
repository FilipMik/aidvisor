package com.filipmik.aidvisor.data.database

import com.filipmik.aidvisor.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeDataSource {

    fun getAllRecipes() : Flow<List<Recipe>>

    suspend fun insertRecipe(recipe: Recipe)
}
