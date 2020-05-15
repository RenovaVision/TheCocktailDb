package com.renovavision.thecocktaildb

import androidx.fragment.app.Fragment
import com.renovavision.thecocktaildb.categories.CategoriesFragment
import com.renovavision.thecocktaildb.cocktails.details.CocktailDetailsFragment
import com.renovavision.thecocktaildb.cocktails.list.CocktailsListFragment
import com.renovavision.thecocktaildb.home.HomeFragment
import com.renovavision.thecocktaildb.ingredients.IngredientsFragment
import com.renovavision.thecocktaildb.inject.FragmentKey
import com.renovavision.thecocktaildb.search.SearchFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentsModule {

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    fun homeFragment(homeFragment: HomeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SearchFragment::class)
    fun searchFragment(searchFragment: SearchFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(IngredientsFragment::class)
    fun ingredientsFragment(ingredientsFragment: IngredientsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CategoriesFragment::class)
    fun categoriesFragment(categoriesFragment: CategoriesFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CocktailsListFragment::class)
    fun cocktailsListFragment(cocktailsListFragment: CocktailsListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CocktailDetailsFragment::class)
    fun cocktailDetailsFragment(cocktailDetailsFragment: CocktailDetailsFragment): Fragment
}