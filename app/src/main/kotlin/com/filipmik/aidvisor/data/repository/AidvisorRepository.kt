package com.filipmik.aidvisor.data.repository

import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.response.ChatCompletionsResponse
import com.filipmik.aidvisor.data.remote.AidvisorServiceImpl
import com.filipmik.aidvisor.tools.ApiResult
import javax.inject.Inject

class AidvisorRepository @Inject constructor(
    private val aidvisorService: AidvisorServiceImpl
) {

    suspend fun getChatCompletions(
        chatCompletionRequest: ChatCompletionRequest
    ): ApiResult<ChatCompletionsResponse> = aidvisorService.getChatCompletions(chatCompletionRequest)
}
