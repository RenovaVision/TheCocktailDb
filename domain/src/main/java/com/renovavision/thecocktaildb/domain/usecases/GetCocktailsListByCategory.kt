package com.renovavision.thecocktaildb.domain.usecases

import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import javax.inject.Inject

class GetCocktailsListByCategory @Inject constructor(private val cocktailsRepo: CocktailsRepository) {

    suspend fun invoke(category: String) = cocktailsRepo.loadCocktailsListByCategory(category)
}