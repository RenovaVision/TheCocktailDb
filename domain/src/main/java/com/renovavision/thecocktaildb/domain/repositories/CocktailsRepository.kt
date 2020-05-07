package com.renovavision.thecocktaildb.domain.repositories

import com.renovavision.thecocktaildb.domain.entities.CocktailInfoEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity

interface CocktailsRepository {

    suspend fun loadCocktailsListByIngredient(ingredient: String): List<DrinkEntity>

    suspend fun loadCocktailsListByCategory(category: String): List<DrinkEntity>

    suspend fun loadCocktailDetails(id: Int): List<CocktailInfoEntity.CocktailEntity>
}