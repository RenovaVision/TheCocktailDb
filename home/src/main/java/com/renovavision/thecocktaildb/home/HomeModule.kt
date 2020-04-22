package com.renovavision.thecocktaildb.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
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
    @ViewModelKey(HomeViewModel::class)
    fun homeViewModel(homeViewModel: HomeViewModel): ViewModel
}