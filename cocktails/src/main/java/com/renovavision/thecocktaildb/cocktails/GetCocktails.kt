package com.renovavision.thecocktaildb.cocktails

import com.renovavision.thecocktaildb.network.data.repo.CocktailsRepo
import javax.inject.Inject

class GetCocktails @Inject constructor(private val cocktailsRepo: CocktailsRepo) {

    suspend fun loadCocktailsListByIngredient(ingredient: String) =
        cocktailsRepo.loadCocktailsListByIngredient(ingredient)

    suspend fun loadCocktailsListByCategory(category: String) =
        cocktailsRepo.loadCocktailsListByCategory(category)

    suspend fun loadCocktailDetails(id: Int) = cocktailsRepo.loadCocktailDetails(id)
}