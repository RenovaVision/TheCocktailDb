package com.renovavision.thecocktaildb.domain.usecases

import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import javax.inject.Inject

class GetCocktailDetails @Inject constructor(private val cocktailsRepo: CocktailsRepository) {

    suspend fun loadCocktailDetails(id: Int) = cocktailsRepo.loadCocktailDetails(id)
}