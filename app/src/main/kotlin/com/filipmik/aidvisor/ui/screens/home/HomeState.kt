package com.filipmik.aidvisor.ui.screens.home

import com.filipmik.aidvisor.domain.model.RecipeListItem

data class HomeState(
    val isLoading: Boolean = false,
    val recipes: List<RecipeListItem> = emptyList(),
    val ingredients: String = "apple, flour, egg, walnuts, butter, cream",
    val error: String = ""
)
