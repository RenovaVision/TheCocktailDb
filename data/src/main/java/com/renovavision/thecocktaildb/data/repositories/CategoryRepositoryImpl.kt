package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.api.CocktailsApi
import com.renovavision.thecocktaildb.data.mapper.Mapper
import com.renovavision.thecocktaildb.domain.repositories.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(private val cocktailsApi: CocktailsApi) :
    CategoryRepository {

    private val mapper = Mapper()

    override suspend fun loadCategories() =
        cocktailsApi.loadDrinksCategory().drinks.map { mapper.mapCategoryToEntity(it) }
}