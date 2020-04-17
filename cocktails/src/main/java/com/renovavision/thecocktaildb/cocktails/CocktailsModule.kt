package com.renovavision.thecocktaildb.cocktails

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.renovavision.thecocktaildb.cocktails.details.CocktailDetailsFragment
import com.renovavision.thecocktaildb.cocktails.details.CocktailDetailsViewModel
import com.renovavision.thecocktaildb.cocktails.list.CocktailsListFragment
import com.renovavision.thecocktaildb.cocktails.list.CocktailsListViewModel
import com.renovavision.thecocktaildb.inject.FragmentKey
import com.renovavision.thecocktaildb.inject.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface CocktailsModule {

    @Binds
    @IntoMap
    @FragmentKey(CocktailsListFragment::class)
    fun cocktailsListFragment(cocktailsListFragment: CocktailsListFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(CocktailsListViewModel::class)
    fun cocktailsListViewModel(cocktailsListViewModel: CocktailsListViewModel): ViewModel

    @Binds
    @IntoMap
    @FragmentKey(CocktailDetailsFragment::class)
    fun cocktailDetailsFragment(cocktailDetailsFragment: CocktailDetailsFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(CocktailDetailsViewModel::class)
    fun cocktailDetailsViewModel(cocktailDetailsViewModel: CocktailDetailsViewModel): ViewModel
}