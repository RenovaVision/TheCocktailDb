package com.renovavision.thecocktaildb.domain.repositories

import com.renovavision.thecocktaildb.domain.entities.CocktailInfoEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import kotlinx.coroutines.flow.Flow

interface CocktailsRepository {

    suspend fun loadCocktailsListByIngredient(ingredient: String): Flow<List<DrinkEntity>>

    suspend fun loadCocktailsListByCategory(category: String): Flow<List<DrinkEntity>>

    suspend fun loadCocktailDetails(id: Int): Flow<List<CocktailInfoEntity.CocktailEntity>>
}