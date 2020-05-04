package com.renovavision.thecocktaildb.domain.repositories

import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.IngredientEntity

interface IngredientsRepository {

    suspend fun loadIngredients(): List<IngredientEntity>
}