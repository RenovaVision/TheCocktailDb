package com.renovavision.thecocktaildb.inject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider

class DaggerFragmentFactory @Inject constructor(
    private val fragmentProviders: MutableMap<Class<out Fragment>, Provider<Fragment>>
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        fragmentProviders.getValue(loadFragmentClass(classLoader, className)).get()
}
