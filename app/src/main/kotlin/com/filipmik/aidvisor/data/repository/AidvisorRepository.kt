package com.filipmik.aidvisor.data.repository

import com.filipmik.aidvisor.data.database.RecipeDataSourceImpl
import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.response.ChatCompletionsResponse
import com.filipmik.aidvisor.data.remote.AidvisorServiceImpl
import com.filipmik.aidvisor.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AidvisorRepository @Inject constructor(
    private val aidvisorService: AidvisorServiceImpl,
    private val recipeDataSourceImpl: RecipeDataSourceImpl
) {

    // Remote data storage

    suspend fun getRecipesResponse(
        chatCompletionRequest: ChatCompletionRequest
    ): ChatCompletionsResponse = aidvisorService.getChatCompletions(chatCompletionRequest)

    // Local data storage

    fun getSavedRecipes() : Flow<List<Recipe>> = recipeDataSourceImpl.getAllRecipes()

    suspend fun saveRecipe(recipe: Recipe) = recipeDataSourceImpl.insertRecipe(recipe)

    suspend fun deleteRecipeByName(recipeName: String) = recipeDataSourceImpl.deleteRecipeByName(recipeName)
}
