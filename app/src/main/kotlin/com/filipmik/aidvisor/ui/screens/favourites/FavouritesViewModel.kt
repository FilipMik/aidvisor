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

    override fun onSortAtoZ() {
        TODO("Not yet implemented")
    }

    override fun onEasy(isEasySelected: Boolean) = with(favouritesState) {
        recipeFilter = recipeFilter.copy(showEasy = isEasySelected.not())
        onFilterChanged()
    }

    override fun onMedium(isMediumSelected: Boolean) = with(favouritesState) {
        recipeFilter = recipeFilter.copy(showMedium = isMediumSelected.not())
        onFilterChanged()
    }

    override fun onHard(isHardSelected: Boolean) = with(favouritesState) {
        recipeFilter = recipeFilter.copy(showHard = isHardSelected.not())
        onFilterChanged()
    }

    private fun onFilterChanged() {
        viewModelScope.launch {
            getFilteredRecipesUseCase.init(favouritesState.recipeFilter).invoke()
                .flowOn(Dispatchers.IO)
                .collect {
                    favouritesState.recipes = it
                }
        }
    }
}
