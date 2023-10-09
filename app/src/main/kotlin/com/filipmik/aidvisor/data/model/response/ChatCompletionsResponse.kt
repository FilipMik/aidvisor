package com.filipmik.aidvisor.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletionsResponse(
    @SerialName("id")
    val id: String,
    @SerialName("object")
    val objectClass: String,
    @SerialName("created")
    val created: Long,
    @SerialName("model")
    val model: String,
    @SerialName("usage")
    val usage: Usage,
    @SerialName("choices")
    val choices: List<Choice>
)

@Serializable
data class Usage(
    @SerialName("prompt_tokens")
    val promptTokens: Int,
    @SerialName("completion_tokens")
    val completionTokens: Int,
    @SerialName("total_tokens")
    val totalTokens: Int
)

@Serializable
data class Choice(
    @SerialName("message")
    val message: Message,
    @SerialName("finish_reason")
    val finishReason: String,
    @SerialName("index")
    val index: Int
)

@Serializable
data class Message(
    @SerialName("role")
    val role: String,
    @SerialName("content")
    val content: String
)
