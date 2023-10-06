package com.filipmik.aidvisor.domain.usecase

import com.filipmik.aidvisor.data.repository.AidvisorRepository
import com.filipmik.aidvisor.domain.model.Recipe
import javax.inject.Inject

class DeleteSavedRecipeUseCase @Inject constructor(
    private val aidvisorRepository: AidvisorRepository
) {

    private lateinit var _recipe: Recipe

    fun init(recipe: Recipe) = apply {
        _recipe = recipe
    }

    suspend operator fun invoke() {
        aidvisorRepository.deleteRecipeByName(_recipe.title)
    }
}
