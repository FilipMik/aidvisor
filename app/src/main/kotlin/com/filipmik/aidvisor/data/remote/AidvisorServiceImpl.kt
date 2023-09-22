package com.filipmik.aidvisor.data.remote

import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.response.ChatCompletionsResponse
import com.filipmik.aidvisor.tools.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class AidvisorServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : AidvisorService {
    override suspend fun getChatCompletions(
        chatCompletionRequest: ChatCompletionRequest
    ): ApiResult<ChatCompletionsResponse> =
        try {
           ApiResult.Success(
               httpClient.post(AidvisorRoutes.POSTS_CHAT_COMPLETION) {
               setBody(chatCompletionRequest)
               contentType(ContentType.Application.Json)
           }.body())
        } catch (e: Exception) {
            ApiResult.Error(e.localizedMessage ?: "Something went wrong")
        }
    }
