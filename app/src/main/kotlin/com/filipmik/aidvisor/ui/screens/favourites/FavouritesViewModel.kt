package com.filipmik.aidvisor.ui.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.usecase.GetSavedRecipesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getSavedRecipesListUseCase: GetSavedRecipesListUseCase
) : ViewModel(), Favourites.Actions {

    private val _favouritesState = FavouritesState()
    val favouritesState = _favouritesState

    init {
        viewModelScope.launch {
            getSavedRecipesListUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .collect {
                _favouritesState.recipes = it
            }
        }
    }

    override fun navigateToRecipeDetail(recipe: Recipe) {
        //TODO("Not yet implemented")
    }
}
