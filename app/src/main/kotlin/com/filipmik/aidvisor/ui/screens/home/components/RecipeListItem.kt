package com.filipmik.aidvisor.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    onItemClick: (Recipe) -> Unit,
    onSaveClick: (Recipe) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(recipe) }
            .padding(16.dp, 12.dp, 16.dp, 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(7f),
            text = recipe.title,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )

        Text(
            modifier = Modifier
                .weight(2f)
                .padding(start = 4.dp),
            text = recipe.difficulty.stringValue.replaceFirstChar {
                it.uppercaseChar()
            },
            color = recipe.difficulty.getColor(),
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .padding(4.dp)
                .clickable { onSaveClick(recipe) }
        ) {
            if (recipe.isFavourite) {
                Icon(imageVector = Icons.Rounded.Favorite, "")
            } else {
                Icon(imageVector = Icons.Rounded.FavoriteBorder, "")
            }
        }
    }
}

@Preview
@Composable
fun RecipeListItemPreview(
    recipe: Recipe = Recipe("Bacon, Egg and Cheese Breakfast Quesadilla", RecipeDifficulty.MEDIUM),
    onItemClick: (Recipe) -> Unit = {},
    onSaveClick: (Recipe) -> Unit = {}
) {
    RecipeListItem(recipe = recipe, onItemClick = onItemClick, onSaveClick = onSaveClick)
}
