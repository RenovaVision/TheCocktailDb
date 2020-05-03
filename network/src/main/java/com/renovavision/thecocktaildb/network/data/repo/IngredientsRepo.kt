package com.renovavision.thecocktaildb.network.data.repo

import com.renovavision.thecocktaildb.network.CocktailsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientsRepo @Inject constructor(private val cocktailsApi: CocktailsApi) {

    suspend fun loadIngredients() = cocktailsApi.loadDrinksIngredient()
}