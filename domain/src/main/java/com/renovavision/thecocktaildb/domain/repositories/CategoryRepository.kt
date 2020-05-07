package com.renovavision.thecocktaildb.domain.repositories

import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity.CategoryEntity

interface CategoryRepository {

    suspend fun loadCategories(): List<CategoryEntity>
}