package com.renovavision.thecocktaildb.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity.CategoryEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.IngredientEntity
import com.renovavision.thecocktaildb.home.categories.CategoriesFragment
import com.renovavision.thecocktaildb.home.databinding.FragmentHomeBinding
import com.renovavision.thecocktaildb.home.ingredients.IngredientsFragment
import com.renovavision.thecocktaildb.utils.TabAdapter
import com.renovavision.thecocktaildb.utils.bindingDelegate
import com.renovavision.thecocktaildb.utils.onViewLifecycle
import javax.inject.Inject
import javax.inject.Named

class HomeFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    @Named("navHomeToSearch")
    private val navHomeToSearch: () -> Unit,
    @Named("navCategoriesToCocktailsList")
    private val navCategoriesToCocktailsList: (category: @JvmSuppressWildcards CategoryEntity) -> Unit,
    @Named("navIngredientsToCocktailsList")
    private val navIngredientsToCocktailsList: (ingredient: @JvmSuppressWildcards IngredientEntity) -> Unit
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
            IngredientsFragment(
                viewModelFactory,
                navIngredientsToCocktailsList
            ), getString(R.string.ingredients)
        )
        tabAdapter.addFragment(
            CategoriesFragment(
                viewModelFactory,
                navCategoriesToCocktailsList
            ), getString(R.string.categories)
        )

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
                        navHomeToSearch()

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