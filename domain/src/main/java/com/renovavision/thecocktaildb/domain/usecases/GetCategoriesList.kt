package com.renovavision.thecocktaildb.domain.usecases

import com.renovavision.thecocktaildb.domain.repositories.CategoryRepository
import javax.inject.Inject

class GetCategoriesList @Inject constructor(private val categoryRepo: CategoryRepository) {

    suspend fun invoke() = categoryRepo.loadCategories()
}