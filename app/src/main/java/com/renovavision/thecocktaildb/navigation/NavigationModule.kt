package com.renovavision.thecocktaildb.navigation

import com.renovavision.thecocktaildb.cocktails.CocktailsNavigator
import com.renovavision.thecocktaildb.home.HomeNavigator
import com.renovavision.thecocktaildb.ui.navigation.Navigator
import dagger.Binds
import dagger.Module

@Module
interface NavigationModule {

    @Binds
    fun provideNavigator(navigator: NavigatorImpl): Navigator

    @Binds
    fun provideCocktailsNavigator(navigator: NavigatorImpl): CocktailsNavigator

    @Binds
    fun provideHomeNavigator(navigator: NavigatorImpl): HomeNavigator

}