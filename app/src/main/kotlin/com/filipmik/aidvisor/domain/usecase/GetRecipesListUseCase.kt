package com.filipmik.aidvisor.domain.usecase

import android.content.res.Resources
import com.filipmik.aidvisor.R
import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.request.Message
import com.filipmik.aidvisor.data.repository.AidvisorRepository
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.tools.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetRecipesListUseCase @Inject constructor(
    private val resources: Resources,
    private val aidvisorRepository: AidvisorRepository
) {

    private var _ingredients: String = ""

    fun init(ingredients: String) = apply {
        _ingredients = ingredients
    }

    operator fun invoke() : Flow<ApiResult<List<Recipe>>> =
        fetchRecipeList()
            .combineTransform<List<Recipe>, List<Recipe>, ApiResult<List<Recipe>>>(
                aidvisorRepository.getSavedRecipes()
            ) { queriedRecipes, savedRecipes ->
                val recipes = queriedRecipes.map { queryRecipe ->
                    queryRecipe.copy(isFavourite = savedRecipes.any { savedRecipe ->
                        queryRecipe.title == savedRecipe.title
                    })
                }
                emit(ApiResult.Success(recipes))
            }.onStart { emit(ApiResult.Loading()) }
            .catch { emit(ApiResult.Error(it.localizedMessage ?: "Unknown error")) }

    private fun fetchRecipeList() : Flow<List<Recipe>> = flow {
            val body = aidvisorRepository.getRecipesResponse(
                ChatCompletionRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        Message(role = "user", content = getIngredientsQuery(_ingredients))
                    ),
                    temperature = 0.7
                )
            ).choices.first().message.content // The response from chatGpt

            val recipeList: List<Recipe> = Json.decodeFromString(body)

            emit(recipeList)
    }

    private fun getIngredientsQuery(ingredients: String): String = if (ingredients.isEmpty()) {
            resources.getString(
                R.string.gpt_query_recipes,
                resources.getString(R.string.gpt_query_any_recipes)
            )
        } else {
            resources.getString(R.string.gpt_query_recipes, ingredients)
        }
}
