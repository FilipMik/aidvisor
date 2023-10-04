package com.filipmik.aidvisor.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.usecase.GetRecipesListUseCase
import com.filipmik.aidvisor.domain.usecase.GetSavedRecipesListUseCase
import com.filipmik.aidvisor.domain.usecase.SaveRecipeUseCase
import com.filipmik.aidvisor.tools.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipesListUseCase: GetRecipesListUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val getSavedRecipesListUseCase: GetSavedRecipesListUseCase
) : ViewModel(), Home.Actions {

    private val _homeState = HomeState()
    val homeState: HomeState = _homeState

    override fun fetchRecipes() {
        with(_homeState) {
            getRecipesListUseCase.init(ingredients).invoke()
                .flowOn(Dispatchers.IO)
                .onEach {
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
                }.launchIn(viewModelScope)
        }
    }

    override fun saveRecipe(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveRecipeUseCase.init(recipe).invoke()
            } catch (error: Throwable) {
                _homeState.error = error.localizedMessage
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
