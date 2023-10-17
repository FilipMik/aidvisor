package com.filipmik.aidvisor.data.model.database

data class RecipeFilterDb(
    val orderByNameAsc: Boolean?,
    val orderByDifficultyAsc: Boolean?,
    val difficultiesList: List<String>
) {

    companion object {
        var DEFAULT_FILTER = RecipeFilterDb(
            orderByNameAsc = null,
            orderByDifficultyAsc = null,
            listOf("easy", "medium", "hard")
        )
    }
}
