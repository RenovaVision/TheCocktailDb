package com.renovavision.thecocktaildb.network.data.repo

import com.renovavision.thecocktaildb.network.CocktailsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientsRepo @Inject constructor(private val cocktailsApi: CocktailsApi) {

    suspend fun loadIngredients() = withContext(Dispatchers.Default) {
        cocktailsApi.loadDrinksIngredient()
    }
}