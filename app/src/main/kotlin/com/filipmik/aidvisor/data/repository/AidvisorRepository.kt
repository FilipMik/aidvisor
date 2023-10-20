package com.filipmik.aidvisor.data.repository

import androidx.datastore.core.DataStore
import com.filipmik.aidvisor.data.database.RecipeDataSourceImpl
import com.filipmik.aidvisor.data.model.database.RecipeFilterDb
import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.response.ChatCompletionsResponse
import com.filipmik.aidvisor.data.remote.AidvisorServiceImpl
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.model.RecipeFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AidvisorRepository @Inject constructor(
    private val aidvisorService: AidvisorServiceImpl,
    private val recipeDataSourceImpl: RecipeDataSourceImpl,
    private val recipeFilterDataStore: DataStore<RecipeFilter>
) {

    // Remote data storage

    suspend fun getRecipesResponse(
        chatCompletionRequest: ChatCompletionRequest
    ): ChatCompletionsResponse = aidvisorService.getChatCompletions(chatCompletionRequest)

    // Proto dataStore

    suspend fun setRecipeFilter(recipeFilter: RecipeFilter) {
        recipeFilterDataStore.updateData {
            recipeFilter
        }
    }

    fun getRecipeFilter(): Flow<RecipeFilter> {
        return recipeFilterDataStore.data
    }

    // Local data storage

    fun getSavedRecipes() : Flow<List<Recipe>> = recipeDataSourceImpl.getAllRecipes()

    fun getFilteredRecipes(recipeFilter: RecipeFilterDb): Flow<List<Recipe>> =
        recipeDataSourceImpl.getAllRecipesFilter(recipeFilter)

    suspend fun saveRecipe(recipe: Recipe) = recipeDataSourceImpl.insertRecipe(recipe)

    suspend fun deleteRecipeByName(recipeName: String) = recipeDataSourceImpl.deleteRecipeByName(recipeName)
}
