package com.renovavision.thecocktaildb.ingredients

import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.IngredientEntity

interface IngredientsNavigator {

    fun navIngredientsToCocktailsList(ingredient: IngredientEntity)
}