package com.renovavision.thecocktaildb.domain.usecases

import com.renovavision.thecocktaildb.domain.repositories.CategoriesRepository
import javax.inject.Inject

class GetCategoriesList @Inject constructor(private val categoriesRepo: CategoriesRepository) {

    suspend fun invoke() = categoriesRepo.loadCategories()
}