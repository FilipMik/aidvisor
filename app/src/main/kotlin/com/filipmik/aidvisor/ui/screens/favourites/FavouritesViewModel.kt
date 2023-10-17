package com.filipmik.aidvisor.ui.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.usecase.DeleteSavedRecipeUseCase
import com.filipmik.aidvisor.domain.usecase.GetFilteredRecipesUseCase
import com.filipmik.aidvisor.ui.screens.favourites.filter.BottomSheet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFilteredRecipesUseCase: GetFilteredRecipesUseCase,
    private val deleteSavedRecipeUseCase: DeleteSavedRecipeUseCase
) : ViewModel(), Favourites.Actions, BottomSheet.Actions {

    private var _favouritesState = FavouritesState()
    val favouritesState = _favouritesState

    init {
        onFilterChanged()
    }

    override fun onCleared() {
        super.onCleared()
    }

    override fun navigateToRecipeDetail(recipe: Recipe) {
        //TODO("Not yet implemented")
    }

    override fun unfavouriteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                deleteSavedRecipeUseCase.init(recipe).invoke()
            } catch (error: Throwable) {
                _favouritesState.error = error.localizedMessage ?: "Unknown error"
            }
        }
    }

    // Sorting by name

    override fun onSortAtoZ(orderByNameAsc: Boolean) = with(favouritesState) {
        recipeFilter = when {
            orderByNameAsc -> recipeFilter.copy(
                orderByNameAsc = null,
                orderByDifficultyAsc = null
            )
            else -> recipeFilter.copy(
                orderByNameAsc = true,
                orderByDifficultyAsc = null
            )
        }
        onFilterChanged()
    }

    override fun onSortZtoA(orderByNameAsc: Boolean) = with(favouritesState) {
        recipeFilter = when {
            orderByNameAsc -> recipeFilter.copy(
                orderByNameAsc = null,
                orderByDifficultyAsc = null
            )
            else -> recipeFilter.copy(
                orderByNameAsc = false,
                orderByDifficultyAsc = null
            )
        }
        onFilterChanged()
    }

    // Sorting by difficulty

    override fun onSortEasyToHard(orderByDifficultyAsc: Boolean) = with(favouritesState) {
        recipeFilter = when {
            orderByDifficultyAsc -> recipeFilter.copy(
                orderByDifficultyAsc = null,
                orderByNameAsc = null
            )
            else -> recipeFilter.copy(
                orderByDifficultyAsc = true,
                orderByNameAsc = null
            )
        }
        onFilterChanged()
    }

    override fun onSortHardToEasy(orderByDifficultyAsc: Boolean) = with(favouritesState) {
        recipeFilter = when {
            orderByDifficultyAsc -> recipeFilter.copy(
                orderByDifficultyAsc = null,
                orderByNameAsc = null
            )
            else -> recipeFilter.copy(
                orderByDifficultyAsc = false,
                orderByNameAsc = null
            )
        }
        onFilterChanged()
    }

    // Filtering difficulties

    override fun onEasy(isEasySelected: Boolean) = with(favouritesState) {
        if (recipeFilter.showMedium || recipeFilter.showHard) {
            recipeFilter = recipeFilter.copy(showEasy = isEasySelected.not())
            onFilterChanged()
        }
    }

    override fun onMedium(isMediumSelected: Boolean) = with(favouritesState) {
        if (recipeFilter.showEasy || recipeFilter.showHard) {
            recipeFilter = recipeFilter.copy(showMedium = isMediumSelected.not())
            onFilterChanged()
        }
    }

    override fun onHard(isHardSelected: Boolean) = with(favouritesState) {
        if (recipeFilter.showEasy || recipeFilter.showMedium) {
            recipeFilter = recipeFilter.copy(showHard = isHardSelected.not())
            onFilterChanged()
        }
    }

    private fun onFilterChanged() {
        // TODO update filter to sharedPreferences

        viewModelScope.launch {

            // TODO GET filter from shared preference and pass it to getFilteredRecipesUseCase

            getFilteredRecipesUseCase.init(favouritesState.recipeFilter).invoke()
                .flowOn(Dispatchers.IO)
                .collect {
                    favouritesState.recipes = it
                }
        }
    }
}
