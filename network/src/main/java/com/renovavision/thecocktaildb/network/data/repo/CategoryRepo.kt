package com.renovavision.thecocktaildb.network.data.repo

import com.renovavision.thecocktaildb.network.CocktailsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepo @Inject constructor(private val cocktailsApi: CocktailsApi) {

    suspend fun loadCategories() = withContext(Dispatchers.Default) {
        cocktailsApi.loadDrinksCategory()
    }
}