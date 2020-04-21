package com.renovavision.thecocktaildb.category

import com.renovavision.thecocktaildb.network.data.repo.CategoryRepo
import javax.inject.Inject

class CategoryUseCase @Inject constructor (private val categoryRepo: CategoryRepo) {

    suspend fun invoke() = categoryRepo.loadCategories()
}