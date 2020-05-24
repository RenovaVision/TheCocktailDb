package com.renovavision.thecocktaildb.data

import com.renovavision.thecocktaildb.data.repositories.CategoryRepositoryImpl
import com.renovavision.thecocktaildb.data.repositories.CocktailsRepositoryImpl
import com.renovavision.thecocktaildb.data.repositories.IngredientsRepositoryImpl
import com.renovavision.thecocktaildb.domain.repositories.CategoryRepository
import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import com.renovavision.thecocktaildb.domain.repositories.IngredientsRepository
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
interface RepositoryModule {

    @Binds
    fun provideCategoryRepository(categoryRepository: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun provideCocktailsRepository(cocktailsRepository: CocktailsRepositoryImpl): CocktailsRepository

    @Binds
    fun provideIngredientsRepository(ingredientsRepository: IngredientsRepositoryImpl): IngredientsRepository
}