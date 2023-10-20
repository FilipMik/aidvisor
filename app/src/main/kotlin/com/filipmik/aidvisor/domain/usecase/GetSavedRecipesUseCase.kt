package com.filipmik.aidvisor.domain.usecase

import com.filipmik.aidvisor.data.repository.AidvisorRepository
import com.filipmik.aidvisor.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSavedRecipesUseCase @Inject constructor(
    private val aidvisorRepository: AidvisorRepository
) {

    operator fun invoke() : Flow<List<Recipe>> = aidvisorRepository.getSavedRecipes().map { recipes ->
        recipes.map {
            it.copy(isFavourite = true)
        }
    }
}

