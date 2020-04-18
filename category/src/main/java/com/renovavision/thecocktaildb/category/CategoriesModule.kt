package com.renovavision.thecocktaildb.category

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.renovavision.thecocktaildb.inject.FragmentKey
import com.renovavision.thecocktaildb.inject.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CategoriesModule {

    @Binds
    @IntoMap
    @FragmentKey(CategoriesFragment::class)
    fun categoriesFragment(categoriesFragment: CategoriesFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    fun categoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel
}