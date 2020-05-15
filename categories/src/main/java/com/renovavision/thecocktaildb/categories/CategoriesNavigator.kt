package com.renovavision.thecocktaildb.categories

import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity.CategoryEntity

interface CategoriesNavigator {

    fun navCategoriesToCocktailsList(category: CategoryEntity)
}