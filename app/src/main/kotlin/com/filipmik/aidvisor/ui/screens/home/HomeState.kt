package com.filipmik.aidvisor.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.filipmik.aidvisor.domain.model.Recipe
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class HomeState {

    var apiCompletionState: ScreenState by mutableStateOf(ScreenState.CONTENT)

    var recipes: List<Recipe> by mutableStateOf(listOf())

    var ingredients: String by mutableStateOf("")

    var error: String? by mutableStateOf("")
}

enum class ScreenState {
    LOADING, CONTENT, ERROR
}
