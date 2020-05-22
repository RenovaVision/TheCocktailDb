package com.renovavision.thecocktaildb.domain.repositories

import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.IngredientEntity
import kotlinx.coroutines.flow.Flow

interface IngredientsRepository {

    suspend fun loadIngredients(): Flow<List<IngredientEntity>>
}