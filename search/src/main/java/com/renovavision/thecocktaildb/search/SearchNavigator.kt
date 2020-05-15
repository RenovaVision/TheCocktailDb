package com.renovavision.thecocktaildb.search

import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity

interface SearchNavigator{

    fun navSearchToCocktailDetails(cocktail: DrinkEntity)
}