package com.renovavision.thecocktaildb.ingredients

import com.renovavision.thecocktaildb.domain.entities.Ingredient

interface IngredientsNavigator {

    fun navIngredientsToCocktailsList(ingredient: Ingredient)
}