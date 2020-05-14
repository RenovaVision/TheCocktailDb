package com.renovavision.thecocktaildb.activity

import androidx.lifecycle.ViewModelProvider
import com.renovavision.thecocktaildb.cocktails.CocktailsModule
import com.renovavision.thecocktaildb.home.HomeModule
import com.renovavision.thecocktaildb.inject.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityModule {

    @Binds
    fun viewModelProviderFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector(
        modules = [
            HomeModule::class,
            CocktailsModule::class,
            NavHostFragmentModule::class
        ]
    )
    fun mainActivity(): MainActivity
}