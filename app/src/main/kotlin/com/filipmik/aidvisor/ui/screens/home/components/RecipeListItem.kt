package com.filipmik.aidvisor.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.model.RecipeDifficulty
import com.filipmik.aidvisor.domain.model.getColor

@Composable
fun RecipeListItem(
    recipe: Recipe,
    onItemClick: (Recipe) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(recipe) }
            .padding(16.dp, 12.dp, 16.dp, 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        Text(
            text = recipe.difficulty.stringValue.replaceFirstChar {
                it.uppercaseChar()
            },
            color = recipe.difficulty.getColor(),
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun RecipeListItemPreview(
    recipe: Recipe = Recipe("Scrambled eggs", RecipeDifficulty.HARD),
    onItemClick: (Recipe) -> Unit = {}
) {
    RecipeListItem(recipe = recipe, onItemClick = onItemClick)
}
