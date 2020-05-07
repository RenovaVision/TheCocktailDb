package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.api.CocktailsApi
import com.renovavision.thecocktaildb.data.mapper.Mapper
import com.renovavision.thecocktaildb.domain.repositories.IngredientsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientsRepositoryImpl @Inject constructor(private val cocktailsApi: CocktailsApi) :
    IngredientsRepository {

    private val mapper = Mapper()

    override suspend fun loadIngredients() =
        cocktailsApi.loadDrinksIngredient().drinks.map { mapper.mapIngredientToEntity(it) }
}