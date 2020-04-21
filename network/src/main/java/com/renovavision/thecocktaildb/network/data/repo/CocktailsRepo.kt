package com.renovavision.thecocktaildb.network.data.repo

import com.renovavision.thecocktaildb.network.CocktailsApi
import com.renovavision.thecocktaildb.network.DrinksByQuery.Drink
import com.renovavision.thecocktaildb.network.DrinksCategory.Category
import com.renovavision.thecocktaildb.network.DrinksIngredient.Ingredient
import com.renovavision.thecocktaildb.network.data.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailsRepo @Inject constructor(private val cocktailsApi: CocktailsApi) {

    suspend fun loadData(data: Data<*>) = when (data) {
        is Ingredient -> loadCocktailsListByIngredient(data.key)
        is Category -> loadCocktailsListByCategory(data.key)
        is Drink -> loadCocktailDetails(data.key)
        else -> throw IllegalArgumentException()
    }

    private suspend fun loadCocktailsListByIngredient(ingredient: String) =
        withContext(Dispatchers.Default) {
            cocktailsApi.loadDrinksByIngredient(ingredient)
        }

    private suspend fun loadCocktailsListByCategory(category: String) =
        withContext(Dispatchers.Default) {
            cocktailsApi.loadDrinksByCategory(category)
        }

    private suspend fun loadCocktailDetails(id: Int) = withContext(Dispatchers.Default) {
        cocktailsApi.loadCocktailInfoById(id)
    }
}