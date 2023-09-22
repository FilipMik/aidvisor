package com.filipmik.aidvisor.domain.usecase

import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.request.Message
import com.filipmik.aidvisor.data.repository.AidvisorRepository
import com.filipmik.aidvisor.domain.model.RecipeListItem
import com.filipmik.aidvisor.tools.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetRecipesListUseCase @Inject constructor(
    private val aidvisorRepository: AidvisorRepository
) {

    private var _ingredients: String = ""

    fun init(ingredients: String) = apply {
        _ingredients = ingredients
    }
    operator fun invoke() : Flow<ApiResult<List<RecipeListItem>>> = flow {
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

            val recipeList: List<RecipeListItem> = Json.decodeFromString(body)

            emit(ApiResult.Success(recipeList))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    private fun getIngredientsQuery(ingredients: String): String {
        return "Create me a JSON array response in this format: \"[{\"title\":\"Banana Pancakes\"}, ...]\"" +
            "Without no other comments. Only the array. " +
            "A list of recipes with these ingredients - $ingredients. With only the titles."
    }
}
