package com.filipmik.aidvisor.data.database

import aidvisor.cache.RecipeEntity
import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.filipmik.aidvisor.RecipeDatabase
import com.filipmik.aidvisor.data.model.database.RecipeFilterDb
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.model.toRecipeList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeDataSourceImpl constructor(
    db: RecipeDatabase
) : RecipeDataSource {

    private val recipeDbQueries = db.recipeDbQueries

    override fun getAllRecipes(): Flow<List<Recipe>> = recipeDbQueries
        .getAllRecipes()
        .toRecipeListFlow()

    override fun getAllRecipesFilter(
        recipeFilterDb: RecipeFilterDb)
    : Flow<List<Recipe>> = recipeDbQueries
        .getAllRecipesFilter(
            difficultyList = recipeFilterDb.difficultiesList,
            nameAsc = recipeFilterDb.orderByNameAsc,
            difficultyAsc = recipeFilterDb.orderByDifficultyAsc
        )
        .toRecipeListFlow()

    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDbQueries.insertRecipe(
            recipe.title,
            recipe.difficulty.stringValue
        )
    }

    override suspend fun deleteRecipeByName(recipeName: String) {
        recipeDbQueries.deleteRecipeByName(recipeName)
    }
}

fun Query<RecipeEntity>.toRecipeListFlow() : Flow<List<Recipe>> = run {
    this.asFlow().mapToList(Dispatchers.IO).map {
        it.toRecipeList()
    }
}
