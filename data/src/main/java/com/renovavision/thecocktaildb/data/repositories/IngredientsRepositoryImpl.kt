package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.api.CocktailsApi
import com.renovavision.thecocktaildb.data.mapper.drinksIngredientToEntityMapper
import com.renovavision.thecocktaildb.domain.repositories.IngredientsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientsRepositoryImpl @Inject constructor(private val cocktailsApi: CocktailsApi) :
    IngredientsRepository {

    override suspend fun loadIngredients() =
        cocktailsApi.loadDrinksIngredient().drinks.map { drinksIngredientToEntityMapper(it) }
}