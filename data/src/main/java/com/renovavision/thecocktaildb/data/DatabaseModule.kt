package com.renovavision.thecocktaildb.data

import android.app.Application
import com.renovavision.thecocktaildb.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) = AppDatabase.getInstance(application)

    @Provides
    @Singleton
    fun provideCategoriesDao(database: AppDatabase) = database.getCategoriesDao()

    @Provides
    @Singleton
    fun provideIngredientsDao(database: AppDatabase) = database.getIngredientsDao()

    @Provides
    @Singleton
    fun provideCocktailsDao(database: AppDatabase) = database.getCocktailsDao()
}