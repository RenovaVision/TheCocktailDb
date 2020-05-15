package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.api.CocktailsApi
import com.renovavision.thecocktaildb.data.mapper.cocktailInfoToEntityMapper
import com.renovavision.thecocktaildb.data.mapper.drinksToEntityMapper
import com.renovavision.thecocktaildb.domain.entities.CocktailInfoEntity.CocktailEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailsRepositoryImpl @Inject constructor(private val cocktailsApi: CocktailsApi) :
    CocktailsRepository {

    override suspend fun loadCocktailsListByIngredient(ingredient: String): List<DrinkEntity> =
        cocktailsApi.loadDrinksByIngredient(ingredient).drinks.map { drinksToEntityMapper(it) }

    override suspend fun loadCocktailsListByCategory(category: String): List<DrinkEntity> =
        cocktailsApi.loadDrinksByCategory(category).drinks.map { drinksToEntityMapper(it) }

    override suspend fun loadCocktailDetails(id: Int): List<CocktailEntity> =
        cocktailsApi.loadCocktailInfoById(id).drinks.map { cocktailInfoToEntityMapper(it) }
}