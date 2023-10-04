package com.filipmik.aidvisor.data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.filipmik.aidvisor.RecipeDatabase
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.model.toRecipeDifficulty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeDataSourceImpl constructor(
    db: RecipeDatabase
) : RecipeDataSource {

    private val recipeDbQueries = db.recipeDbQueries

    override fun getAllRecipes(): Flow<List<Recipe>> = recipeDbQueries
        .getAllRecipes()
        .asFlow()
        .mapToList(Dispatchers.IO)
        .map {
            it.map { recipeEntry ->
                Recipe(recipeEntry.name, recipeEntry.difficulty.toRecipeDifficulty())
            }
        }

    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDbQueries.insertRecipe(
            recipe.title,
            recipe.difficulty.stringValue
        )
    }
}
