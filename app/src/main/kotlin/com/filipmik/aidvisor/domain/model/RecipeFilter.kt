package com.filipmik.aidvisor.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeFilter(
    val orderByNameAsc: Boolean? = null,
    val orderByDifficultyAsc: Boolean? = null,
    val showEasy: Boolean = true,
    val showMedium: Boolean = true,
    val showHard: Boolean = true,
)
