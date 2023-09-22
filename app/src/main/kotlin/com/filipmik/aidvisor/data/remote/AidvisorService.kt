package com.filipmik.aidvisor.data.remote

import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.response.ChatCompletionsResponse

interface AidvisorService {

    suspend fun getChatCompletions(
        chatCompletionRequest: ChatCompletionRequest
    ) : ChatCompletionsResponse
}
