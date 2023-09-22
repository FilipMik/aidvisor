package com.filipmik.aidvisor.data.remote

import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.response.ChatCompletionsResponse
import com.filipmik.aidvisor.tools.ApiResult

interface AidvisorService {

    suspend fun getChatCompletions(
        chatCompletionRequest: ChatCompletionRequest
    ) : ApiResult<ChatCompletionsResponse>
}
