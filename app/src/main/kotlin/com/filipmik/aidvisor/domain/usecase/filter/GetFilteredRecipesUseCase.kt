package com.filipmik.aidvisor.domain.usecase.filter

import com.filipmik.aidvisor.data.model.database.RecipeFilterDb
import com.filipmik.aidvisor.data.repository.AidvisorRepository
import com.filipmik.aidvisor.domain.model.Recipe
import com.filipmik.aidvisor.domain.model.RecipeDifficulty
import com.filipmik.aidvisor.domain.model.RecipeFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetFilteredRecipesUseCase @Inject constructor(
    private val aidvisorRepository: AidvisorRepository
) {

    operator fun invoke(): Flow<Pair<List<Recipe>, RecipeFilter>> {
        return aidvisorRepository.getRecipeFilter().flatMapConcat { filter ->
            val difficultiesList = mutableListOf<String>()

            with(difficultiesList) {
                if (filter.showEasy) {
                    add(RecipeDifficulty.EASY.stringValue)
                }
                if (filter.showMedium) {
                    add(RecipeDifficulty.MEDIUM.stringValue)
                }
                if (filter.showHard) {
                    add(RecipeDifficulty.HARD.stringValue)
                }
            }

           aidvisorRepository.getFilteredRecipes(
                RecipeFilterDb(
                    orderByNameAsc = filter.orderByNameAsc,
                    orderByDifficultyAsc = filter.orderByDifficultyAsc,
                    difficultiesList = difficultiesList
                )
            ).map { recipeList ->
                recipeList.map { recipe -> recipe.copy(isFavourite = true) } to filter
            }
        }
    }
}
