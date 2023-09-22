package com.filipmik.aidvisor.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipmik.aidvisor.data.model.request.ChatCompletionRequest
import com.filipmik.aidvisor.data.model.request.Message
import com.filipmik.aidvisor.data.model.response.ChatCompletionsResponse
import com.filipmik.aidvisor.data.repository.AidvisorServiceImpl
import com.filipmik.aidvisor.tools.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val aidvisorServiceImpl: AidvisorServiceImpl
) : ViewModel() {

    val chatCompletionsResponse =
        MutableStateFlow<ApiResult<ChatCompletionsResponse>?>(null)

    init {
        viewModelScope.launch {
            chatCompletionsResponse.value = aidvisorServiceImpl.getChatCompletions(
                ChatCompletionRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        Message(role = "user", content = "Tell me a joke")
                    ),
                    temperature = 0.7
                )
            )
        }
    }
}
