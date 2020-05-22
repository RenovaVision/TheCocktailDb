package com.renovavision.thecocktaildb.activity

import androidx.lifecycle.ViewModel
import com.renovavision.thecocktaildb.cocktails.details.CocktailDetailsViewModel
import com.renovavision.thecocktaildb.cocktails.list.CocktailsListViewModel
import com.renovavision.thecocktaildb.categories.CategoriesViewModel
import com.renovavision.thecocktaildb.ingredients.IngredientsViewModel
import com.renovavision.thecocktaildb.search.SearchViewModel
import com.renovavision.thecocktaildb.inject.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun searchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IngredientsViewModel::class)
    fun ingredientsViewModel(ingredientsViewModel: IngredientsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    fun categoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CocktailsListViewModel::class)
    fun cocktailsListViewModel(cocktailsListViewModel: CocktailsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CocktailDetailsViewModel::class)
    fun cocktailDetailsViewModel(cocktailDetailsViewModel: CocktailDetailsViewModel): ViewModel
}