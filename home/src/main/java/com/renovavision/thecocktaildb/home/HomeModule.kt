package com.renovavision.thecocktaildb.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.renovavision.thecocktaildb.home.categories.CategoriesFragment
import com.renovavision.thecocktaildb.home.categories.CategoriesViewModel
import com.renovavision.thecocktaildb.home.ingredients.IngredientsFragment
import com.renovavision.thecocktaildb.home.ingredients.IngredientsViewModel
import com.renovavision.thecocktaildb.home.search.SearchFragment
import com.renovavision.thecocktaildb.home.search.SearchViewModel
import com.renovavision.thecocktaildb.inject.FragmentKey
import com.renovavision.thecocktaildb.inject.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HomeModule {

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
    @ViewModelKey(SearchViewModel::class)
    fun searchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @FragmentKey(IngredientsFragment::class)
    fun ingredientsFragment(ingredientsFragment: IngredientsFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(IngredientsViewModel::class)
    fun ingredientsViewModel(ingredientsViewModel: IngredientsViewModel): ViewModel

    @Binds
    @IntoMap
    @FragmentKey(CategoriesFragment::class)
    fun categoriesFragment(categoriesFragment: CategoriesFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    fun categoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel
}