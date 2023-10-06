package com.filipmik.aidvisor.domain.model

import androidx.compose.ui.graphics.Color
import com.filipmik.aidvisor.ui.theme.Yellow
import com.filipmik.aidvisor.ui.theme.Green
import com.filipmik.aidvisor.ui.theme.DarkOrange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val title: String,
    val difficulty: RecipeDifficulty,
    var isFavourite: Boolean = false
)

@Serializable
enum class RecipeDifficulty(val stringValue: String) {
    @SerialName("easy")
    EASY("easy"),
    @SerialName("medium")
    MEDIUM("medium"),
    @SerialName("hard")
    HARD("hard")
}

fun RecipeDifficulty.getColor(): Color {
    return when (this) {
        RecipeDifficulty.EASY -> Green
        RecipeDifficulty.MEDIUM -> Yellow
        RecipeDifficulty.HARD -> DarkOrange
    }
}

fun String.toRecipeDifficulty(): RecipeDifficulty {
    return when (this) {
        "easy" -> RecipeDifficulty.EASY
        "medium" -> RecipeDifficulty.MEDIUM
        else -> RecipeDifficulty.HARD
    }
}
