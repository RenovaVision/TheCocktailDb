package com.renovavision.thecocktaildb.data

import com.renovavision.thecocktaildb.data.repositories.CategoriesRepositoryImpl
import com.renovavision.thecocktaildb.data.repositories.CocktailsRepositoryImpl
import com.renovavision.thecocktaildb.data.repositories.IngredientsRepositoryImpl
import com.renovavision.thecocktaildb.domain.repositories.CategoriesRepository
import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import com.renovavision.thecocktaildb.domain.repositories.IngredientsRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun provideCategoryRepository(categoryRepository: CategoriesRepositoryImpl): CategoriesRepository

    @Binds
    fun provideCocktailsRepository(cocktailsRepository: CocktailsRepositoryImpl): CocktailsRepository

    @Binds
    fun provideIngredientsRepository(ingredientsRepository: IngredientsRepositoryImpl): IngredientsRepository
}