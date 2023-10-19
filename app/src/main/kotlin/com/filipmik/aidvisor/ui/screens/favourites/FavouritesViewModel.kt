package com.filipmik.aidvisor.ui.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.model.RecipeFilter
import com.filipmik.aidvisor.domain.usecase.DeleteSavedRecipeUseCase
import com.filipmik.aidvisor.domain.usecase.filter.GetFilteredRecipesUseCase
import com.filipmik.aidvisor.domain.usecase.filter.SetFilterUseCase
import com.filipmik.aidvisor.ui.screens.favourites.filter.BottomSheet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFilteredRecipesUseCase: GetFilteredRecipesUseCase,
    private val deleteSavedRecipeUseCase: DeleteSavedRecipeUseCase,
    private val setFilterUseCase: SetFilterUseCase
) : ViewModel(), Favourites.Actions, BottomSheet.Actions {

    private var _favouritesState = FavouritesState()
    val favouritesState = _favouritesState

    init {
        viewModelScope.launch {
            getFilteredRecipesUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .collect {
                    favouritesState.recipes = it.first
                    favouritesState.recipeFilter = it.second
                }
        }
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
        updateFilter( when {
            orderByNameAsc -> recipeFilter.copy(
                orderByNameAsc = null,
                orderByDifficultyAsc = null
            )
            else -> recipeFilter.copy(
                orderByNameAsc = true,
                orderByDifficultyAsc = null
            )
        })
    }

    override fun onSortZtoA(orderByNameAsc: Boolean) = with(favouritesState) {
        updateFilter( when {
            orderByNameAsc -> recipeFilter.copy(
                orderByNameAsc = null,
                orderByDifficultyAsc = null
            )
            else -> recipeFilter.copy(
                orderByNameAsc = false,
                orderByDifficultyAsc = null
            )
        })
    }

    // Sorting by difficulty

    override fun onSortEasyToHard(orderByDifficultyAsc: Boolean) = with(favouritesState) {
        updateFilter( when {
            orderByDifficultyAsc -> recipeFilter.copy(
                orderByDifficultyAsc = null,
                orderByNameAsc = null
            )
            else -> recipeFilter.copy(
                orderByDifficultyAsc = true,
                orderByNameAsc = null
            )
        })
    }

    override fun onSortHardToEasy(orderByDifficultyAsc: Boolean) = with(favouritesState) {
        updateFilter( when {
            orderByDifficultyAsc -> recipeFilter.copy(
                orderByDifficultyAsc = null,
                orderByNameAsc = null
            )
            else -> recipeFilter.copy(
                orderByDifficultyAsc = false,
                orderByNameAsc = null
            )
        })
    }

    // Filtering difficulties

    override fun onEasy(isEasySelected: Boolean) = with(favouritesState) {
        if (recipeFilter.showMedium || recipeFilter.showHard) {
            updateFilter(recipeFilter = recipeFilter.copy(showEasy = isEasySelected.not()))
        }
    }

    override fun onMedium(isMediumSelected: Boolean) = with(favouritesState) {
        if (recipeFilter.showEasy || recipeFilter.showHard) {
            updateFilter(recipeFilter.copy(showMedium = isMediumSelected.not()))
        }
    }

    override fun onHard(isHardSelected: Boolean) = with(favouritesState) {
        if (recipeFilter.showEasy || recipeFilter.showMedium) {
            updateFilter(recipeFilter.copy(showHard = isHardSelected.not()))
        }
    }

    private fun updateFilter(recipeFilter: RecipeFilter) {
        viewModelScope.launch { setFilterUseCase.init(recipeFilter).invoke() }
    }
}
