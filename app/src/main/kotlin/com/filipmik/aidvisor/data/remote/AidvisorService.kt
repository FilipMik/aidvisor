package com.filipmik.aidvisor.data.remote

import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.response.ChatCompletionsResponse
import kotlinx.coroutines.flow.Flow

interface AidvisorService {

    suspend fun getChatCompletions(
        chatCompletionRequest: ChatCompletionRequest
    ) : ChatCompletionsResponse
}
