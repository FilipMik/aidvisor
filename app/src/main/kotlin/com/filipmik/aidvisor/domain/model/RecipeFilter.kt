package com.filipmik.aidvisor.domain.model

data class RecipeFilter(
    val orderByNameAsc: Boolean?,
    val orderByDifficultyAsc: Boolean?,
    val showEasy: Boolean,
    val showMedium: Boolean,
    val showHard: Boolean,
) {
    companion object {
        var DEFAULT = RecipeFilter(
            orderByNameAsc = null,
            orderByDifficultyAsc = null,
            showEasy = true,
            showMedium = true,
            showHard = true
        )
    }
}
