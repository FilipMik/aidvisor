package com.filipmik.aidvisor.ui.screens.favourites.filter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.filipmik.aidvisor.domain.model.RecipeFilter
import com.filipmik.aidvisor.ui.screens.favourites.FavouritesViewModel

@Composable
fun FilterBottomSheet(
    viewModel: FavouritesViewModel
) {
    BottomSheet.Content(viewModel)
}

object BottomSheet {

    interface Actions {
        fun onSortAtoZ(orderByNameAsc: Boolean)

        fun onSortZtoA(orderByNameAsc: Boolean)

        fun onSortEasyToHard(orderByDifficultyAsc: Boolean)

        fun onSortHardToEasy(orderByDifficultyAsc: Boolean)

        fun onEasy(isEasySelected: Boolean)

        fun onMedium(isMediumSelected: Boolean)

        fun onHard(isHardSelected: Boolean)
    }

    @Composable
    fun Content(viewModel: FavouritesViewModel) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            FilterFlowRow(
                actions = viewModel,
                recipeFilter = viewModel.favouritesState.recipeFilter)
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun FilterFlowRow(
        actions: Actions,
        recipeFilter: RecipeFilter
    ) {

        // If sort A-Z/Z-A then it wont be ordered by difficulty
        // Only if A-Z/Z-A is null, then items will be ordered by their difficulty
        // If nothing is selected, recipes will be displayed by default order

        with(recipeFilter) {
            Column {
                FlowRow(modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)) {
                    FilterItem(
                        selected = orderByNameAsc == true,
                        onClick = { actions.onSortAtoZ(orderByNameAsc == true) },
                        label = "Sort: A-Z"
                    )
                    FilterItem(
                        selected = orderByNameAsc == false,
                        onClick = { actions.onSortZtoA(orderByNameAsc == false) },
                        label = "Sort: Z-A"
                    )

                    FilterItem(
                        selected = orderByDifficultyAsc == true,
                        onClick = { actions.onSortEasyToHard(orderByNameAsc == true) },
                        label = "Sort: Easy-Hard"
                    )
                    FilterItem(
                        selected = orderByDifficultyAsc == false,
                        onClick = { actions.onSortHardToEasy(orderByDifficultyAsc == false) },
                        label = "Sort: Hard-Easy"
                    )
                }

                FlowRow(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
                    FilterItem(
                        selected = showEasy,
                        onClick = { actions.onEasy(showEasy) },
                        label = "Easy"
                    )
                    FilterItem(
                        selected = showMedium,
                        onClick = { actions.onMedium(showMedium) },
                        label = "Medium"
                    )
                    FilterItem(
                        selected = showHard,
                        onClick = { actions.onHard(showHard) },
                        label = "Hard"
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FilterItem(
        selected: Boolean,
        onClick: () -> Unit,
        label: String) {

        FilterChip(
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            selected = selected,
            onClick = { onClick() },
            label = {
                Text(text = label)
            }
        )
    }
}
