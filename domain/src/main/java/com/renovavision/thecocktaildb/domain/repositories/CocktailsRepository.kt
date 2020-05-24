package com.renovavision.thecocktaildb.domain.repositories

import com.renovavision.thecocktaildb.domain.entities.Cocktail
import com.renovavision.thecocktaildb.domain.entities.CocktailDetails
import kotlinx.coroutines.flow.Flow

interface CocktailsRepository {

    suspend fun loadCocktailsListByIngredient(ingredient: String): Flow<List<Cocktail>>

    suspend fun loadCocktailsListByCategory(category: String): Flow<List<Cocktail>>

    suspend fun loadCocktailDetails(id: Int): Flow<CocktailDetails>
}