package com.renovavision.thecocktaildb.home

import androidx.fragment.app.Fragment
import com.renovavision.thecocktaildb.inject.FragmentKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HomeModule {

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    fun homeFragment(homeFragment: HomeFragment): Fragment
}