package com.filipmik.aidvisor.domain.usecase

import com.filipmik.aidvisor.data.model.database.RecipeFilterDb
import com.filipmik.aidvisor.data.repository.AidvisorRepository
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.model.RecipeDifficulty
import com.filipmik.aidvisor.domain.model.RecipeFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilteredRecipesUseCase @Inject constructor(
    private val aidvisorRepository: AidvisorRepository
) {

    private var _filter: RecipeFilter = RecipeFilter()

    fun init(filter: RecipeFilter) = this.apply {
        _filter = filter
    }

    operator fun invoke() : Flow<List<Recipe>> {
        val difficultiesList = mutableListOf<String>()

        with(difficultiesList) {
            if (_filter.showEasy) {
                add(RecipeDifficulty.EASY.stringValue)
            }
            if (_filter.showMedium) {
                add(RecipeDifficulty.MEDIUM.stringValue)
            }
            if (_filter.showHard) {
                add(RecipeDifficulty.HARD.stringValue)
            }
        }

        return aidvisorRepository
            .getFilteredRecipes(
                RecipeFilterDb(
                    orderByNameAsc = _filter.orderByNameAsc,
                    orderByDifficultyAsc = _filter.orderByDifficultyAsc,
                    difficultiesList = difficultiesList
                )
            ).map { recipeList ->
                recipeList.map { recipe ->
                    recipe.copy(isFavourite = true)
                }
            }
    }
}
