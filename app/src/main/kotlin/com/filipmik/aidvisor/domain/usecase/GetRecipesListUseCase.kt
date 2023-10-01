package com.filipmik.aidvisor.domain.usecase

import android.content.res.Resources
import com.filipmik.aidvisor.R
import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.request.Message
import com.filipmik.aidvisor.data.repository.AidvisorRepository
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.tools.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    operator fun invoke() : Flow<ApiResult<List<Recipe>>> = flow {
        emit(ApiResult.Loading())

        try {
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

            emit(ApiResult.Success(recipeList))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.localizedMessage ?: "Unknown error"))
        }
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
