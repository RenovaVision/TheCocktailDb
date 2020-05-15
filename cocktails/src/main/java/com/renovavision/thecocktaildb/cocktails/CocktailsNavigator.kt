package com.renovavision.thecocktaildb.cocktails

import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity

interface CocktailsNavigator {
    fun navCocktailsListToDetails(cocktail: DrinksByQueryEntity.DrinkEntity)
}