package com.renovavision.thecocktaildb.network.data.repo

import com.renovavision.thecocktaildb.network.CocktailsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailsRepo @Inject constructor(private val cocktailsApi: CocktailsApi) {

    suspend fun loadCocktailsListByIngredient(ingredient: String) =
        cocktailsApi.loadDrinksByIngredient(ingredient)

    suspend fun loadCocktailsListByCategory(category: String) =
        cocktailsApi.loadDrinksByCategory(category)

    suspend fun loadCocktailDetails(id: Int) = cocktailsApi.loadCocktailInfoById(id)
}