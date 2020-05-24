package com.renovavision.thecocktaildb.categories

import com.renovavision.thecocktaildb.domain.entities.Category

interface CategoriesNavigator {

    fun navCategoriesToCocktailsList(category: Category)
}