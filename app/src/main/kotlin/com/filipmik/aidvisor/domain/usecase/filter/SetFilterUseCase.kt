package com.filipmik.aidvisor.domain.usecase.filter

import com.filipmik.aidvisor.data.repository.AidvisorRepository
import com.filipmik.aidvisor.domain.model.RecipeFilter
import javax.inject.Inject

class SetFilterUseCase @Inject constructor(
    private val aidvisorRepository: AidvisorRepository
) {

    private var _recipeFilter = RecipeFilter()

    fun init(recipeFilter: RecipeFilter) = this.apply {
        _recipeFilter = recipeFilter
    }

   suspend operator fun invoke() {
        aidvisorRepository.setRecipeFilter(_recipeFilter)
    }
}
