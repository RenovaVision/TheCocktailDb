package com.renovavision.thecocktaildb.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.renovavision.thecocktaildb.R
import com.renovavision.thecocktaildb.categories.CategoriesFragment
import com.renovavision.thecocktaildb.databinding.FragmentHomeBinding
import com.renovavision.thecocktaildb.ingredients.IngredientsFragment
import com.renovavision.thecocktaildb.ui.utils.TabAdapter
import com.renovavision.thecocktaildb.ui.utils.bindingDelegate
import com.renovavision.thecocktaildb.ui.utils.onViewLifecycle
import javax.inject.Inject

class HomeFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val homeNavigator: HomeNavigator
) : Fragment(R.layout.fragment_home) {

    private val binding by bindingDelegate(FragmentHomeBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appSettingPrefs = context?.getSharedPreferences("AppSettingPrefs", 0)
        val sharedPrefsEdit = appSettingPrefs?.edit()
        val isNightModeOn = appSettingPrefs?.getBoolean("NightMode", false)

        if (isNightModeOn == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val tabAdapter = TabAdapter(childFragmentManager)
        tabAdapter.addFragment(
            IngredientsFragment(viewModelFactory),
            getString(R.string.ingredients)
        )
        tabAdapter.addFragment(CategoriesFragment(viewModelFactory), getString(R.string.categories))

        onViewLifecycle({ binding.toolbar }, {
            title = getString(R.string.home)
            inflateMenu(R.menu.main_menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_theme -> {
                        val mode =
                            if (isNightModeOn == false) {
                                sharedPrefsEdit?.putBoolean("NightMode", true)
                                sharedPrefsEdit?.apply()
                                AppCompatDelegate.MODE_NIGHT_YES
                            } else {
                                sharedPrefsEdit?.putBoolean("NightMode", false)
                                sharedPrefsEdit?.apply()
                                AppCompatDelegate.MODE_NIGHT_NO
                            }

                        AppCompatDelegate.setDefaultNightMode(mode)
                        true
                    }
                    R.id.action_search -> {
                        homeNavigator.navHomeToSearch()
                        true
                    }

                    else -> true
                }
            }
        })

        onViewLifecycle({ binding.viewPager }, {
            adapter = tabAdapter
        })

        onViewLifecycle({ binding.tabLayout }, {
            setupWithViewPager(binding.viewPager)
        })
    }
}