package com.filipmik.aidvisor.data.repository

import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.response.ChatCompletionsResponse
import com.filipmik.aidvisor.data.remote.AidvisorServiceImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AidvisorRepository @Inject constructor(
    private val aidvisorService: AidvisorServiceImpl
) {

    suspend fun getRecipesResponse(
        chatCompletionRequest: ChatCompletionRequest
    ): ChatCompletionsResponse = aidvisorService.getChatCompletions(chatCompletionRequest)
}
