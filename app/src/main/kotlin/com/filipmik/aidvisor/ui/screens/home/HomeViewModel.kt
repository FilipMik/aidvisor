package com.filipmik.aidvisor.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.usecase.DeleteSavedRecipeUseCase
import com.filipmik.aidvisor.domain.usecase.GetRecipesListUseCase
import com.filipmik.aidvisor.domain.usecase.SaveRecipeUseCase
import com.filipmik.aidvisor.tools.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipesListUseCase: GetRecipesListUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val deleteSavedRecipeUseCase: DeleteSavedRecipeUseCase
) : ViewModel(), Home.Actions {

    private var _homeState = HomeState()
    val homeState: HomeState = _homeState

    override fun fetchRecipes() {
        viewModelScope.launch {
            with(_homeState) {
                getRecipesListUseCase.init(ingredients).invoke()
                    .flowOn(Dispatchers.IO)
                    .collect {
                        when (it) {
                            is ApiResult.Error -> {
                                apiCompletionState = ScreenState.ERROR
                                error = it.message ?: "Unknown error"
                            }
                            is ApiResult.Loading -> {
                                apiCompletionState = ScreenState.LOADING
                            }
                            is ApiResult.Success -> {
                                apiCompletionState = ScreenState.CONTENT
                                recipes = it.data ?: listOf()
                            }
                        }
                    }
            }
        }
    }

    override fun onFavClick(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            when (recipe.isFavourite) {
                true -> {
                    try {
                        deleteSavedRecipeUseCase.init(recipe).invoke()
                    } catch (error: Throwable) {
                        _homeState.error = error.localizedMessage
                    }
                }
                false -> {
                    try {
                        saveRecipeUseCase.init(recipe).invoke()
                    } catch (error: Throwable) {
                        _homeState.error = error.localizedMessage
                    }
                }
            }
        }
    }

    override fun navigateToDetailScreen() {
        //TODO("Not yet implemented")
    }

    override fun onRetry() {
        //TODO("Not yet implemented")
    }

    override fun setIngredients(ingredients: String) {
        _homeState.ingredients = ingredients
    }
}
