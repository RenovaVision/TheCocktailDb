package com.renovavision.thecocktaildb.domain.usecases

import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import javax.inject.Inject

class GetCocktails @Inject constructor(private val cocktailsRepo: CocktailsRepository) {

    suspend fun loadCocktailsListByIngredient(ingredient: String) =
        cocktailsRepo.loadCocktailsListByIngredient(ingredient)

    suspend fun loadCocktailsListByCategory(category: String) =
        cocktailsRepo.loadCocktailsListByCategory(category)

    suspend fun loadCocktailDetails(id: Int) = cocktailsRepo.loadCocktailDetails(id)
}