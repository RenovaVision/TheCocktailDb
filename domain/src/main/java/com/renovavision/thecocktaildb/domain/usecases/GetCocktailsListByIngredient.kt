package com.renovavision.thecocktaildb.domain.usecases

import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import javax.inject.Inject

class GetCocktailsListByIngredient @Inject constructor(private val cocktailsRepo: CocktailsRepository) {

    suspend fun invoke(ingredient: String) =
        cocktailsRepo.loadCocktailsListByIngredient(ingredient)
}