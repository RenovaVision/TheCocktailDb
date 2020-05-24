package com.renovavision.thecocktaildb.domain.repositories

import com.renovavision.thecocktaildb.domain.entities.Ingredient
import kotlinx.coroutines.flow.Flow

interface IngredientsRepository {

    suspend fun loadIngredients(): Flow<List<Ingredient>>
}