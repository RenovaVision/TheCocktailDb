package com.renovavision.thecocktaildb.ingredients

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.renovavision.thecocktaildb.inject.FragmentKey
import com.renovavision.thecocktaildb.inject.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface IngredientsModule {

    @Binds
    @IntoMap
    @FragmentKey(IngredientsFragment::class)
    fun ingredientsFragment(ingredientsFragment: IngredientsFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(IngredientsViewModel::class)
    fun ingredientsViewModel(ingredientsViewModel: IngredientsViewModel): ViewModel
}