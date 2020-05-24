package com.renovavision.thecocktaildb.domain.repositories

import com.renovavision.thecocktaildb.domain.entities.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    suspend fun loadCategories(): Flow<List<Category>>
}