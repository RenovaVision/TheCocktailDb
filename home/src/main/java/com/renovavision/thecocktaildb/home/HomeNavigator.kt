package com.renovavision.thecocktaildb.home

import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity

interface HomeNavigator {

    fun navHomeToSearch()

    fun navIngredientsToCocktailsList(ingredient: DrinksIngredientEntity.IngredientEntity)

    fun navCategoriesToCocktailsList(category: DrinksCategoryEntity.CategoryEntity)

    fun navSearchToCocktailDetails(cocktail: DrinksByQueryEntity.DrinkEntity)
}