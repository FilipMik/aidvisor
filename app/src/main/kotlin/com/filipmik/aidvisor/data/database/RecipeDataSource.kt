package com.filipmik.aidvisor.data.database

import com.filipmik.aidvisor.data.model.database.RecipeFilterDb
import com.filipmik.aidvisor.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeDataSource {

    fun getAllRecipes() : Flow<List<Recipe>>

    fun getAllRecipesFilter(recipeFilterDb: RecipeFilterDb): Flow<List<Recipe>>

    suspend fun insertRecipe(recipe: Recipe)

    suspend fun deleteRecipeByName(recipeName: String)
}
