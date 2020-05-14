package com.renovavision.thecocktaildb.activity

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.renovavision.thecocktaildb.inject.FragmentKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
internal object NavHostFragmentModule {

    @Provides
    @IntoMap
    @FragmentKey(NavHostFragment::class)
    fun navHostFragment(): Fragment = NavHostFragment()
}