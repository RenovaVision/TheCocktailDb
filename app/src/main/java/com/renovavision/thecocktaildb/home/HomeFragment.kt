package com.renovavision.thecocktaildb.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_home) {

    private val binding by bindingDelegate(FragmentHomeBinding::bind)

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                        viewModel.dispatch(ChangeTheme)

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