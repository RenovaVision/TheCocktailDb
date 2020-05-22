package com.renovavision.thecocktaildb.activity.navigation

import com.renovavision.thecocktaildb.categories.CategoriesNavigator
import com.renovavision.thecocktaildb.cocktails.CocktailsNavigator
import com.renovavision.thecocktaildb.ingredients.IngredientsNavigator
import com.renovavision.thecocktaildb.ui.navigation.Navigator
import dagger.Binds
import dagger.Module

@Module
interface NavigationModule {

    @Binds
    fun provideNavigator(navigator: NavigatorImpl): Navigator

    @Binds
    fun provideIngredientsNavigator(navigator: NavigatorImpl): IngredientsNavigator

    @Binds
    fun provideCategoriesNavigator(navigator: NavigatorImpl): CategoriesNavigator

    @Binds
    fun provideCocktailsNavigator(navigator: NavigatorImpl): CocktailsNavigator
}