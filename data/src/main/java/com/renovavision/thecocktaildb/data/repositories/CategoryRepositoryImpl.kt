package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.api.CocktailsApi
import com.renovavision.thecocktaildb.data.mapper.drinksCategoryToEntityMapper
import com.renovavision.thecocktaildb.domain.repositories.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(private val cocktailsApi: CocktailsApi) :
    CategoryRepository {

    override suspend fun loadCategories() =
        cocktailsApi.loadDrinksCategory().drinks.map { drinksCategoryToEntityMapper(it) }
}