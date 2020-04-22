package com.renovavision.thecocktaildb.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renovavision.thecocktaildb.home.databinding.FragmentHomeBinding
import com.renovavision.thecocktaildb.network.DrinksByQuery.Drink
import com.renovavision.thecocktaildb.utils.bindingDelegate
import com.renovavision.thecocktaildb.utils.observe
import com.renovavision.thecocktaildb.utils.onViewLifecycle
import javax.inject.Inject
import javax.inject.Named

class HomeFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    @Named("navHomeToIngredientList")
    private val navHomeToIngredientList: () -> Unit,
    @Named("navHomeToCategoryList")
    private val navHomeToCategoryList: () -> Unit,
    @Named("navHomeToCocktailDetails")
    private val navHomeToCocktailDetails: (cocktail: @JvmSuppressWildcards Drink) -> Unit
) : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private val binding by bindingDelegate(FragmentHomeBinding::bind)

    private val searchAdapter = SearchResultAdapter { viewModel.dispatch(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appSettingPrefs = context?.getSharedPreferences("AppSettingPrefs", 0)
        val sharedPrefsEdit = appSettingPrefs?.edit()
        val isNightModeOn = appSettingPrefs?.getBoolean("NightMode", false)

        onViewLifecycle({ binding.categoriesButton },
            {
                setOnClickListener { navHomeToCategoryList() }
            }, {
                setOnClickListener(null)
            }
        )

        onViewLifecycle({ binding.searchResult },
            {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = searchAdapter
            })

        onViewLifecycle({ binding.ingredientsButton },
            {
                setOnClickListener { navHomeToIngredientList() }
            }, {
                setOnClickListener(null)
            }
        )

        onViewLifecycle({ binding.switchBtn },
            {
                isChecked = if (isNightModeOn == true) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    false
                }

                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        sharedPrefsEdit?.putBoolean("NightMode", true)
                        sharedPrefsEdit?.apply()
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        sharedPrefsEdit?.putBoolean("NightMode", false)
                        sharedPrefsEdit?.apply()
                    }
                }
            }, { setOnCheckedChangeListener(null) })

        onViewLifecycle({ binding.searchText },
            {
                doAfterTextChanged { editable ->
                    viewModel.dispatch(LoadCocktails(editable.toString()))
                }
            })

        onViewLifecycle({ binding.container },
            {
                setOnClickListener {
                    clearFocus()
                }
            }, {
                setOnClickListener(null)
            })
    }

    override fun onStart() {
        super.onStart()

        viewModel.state.observe(this) {
            handleSearchResult(it)
        }

        viewModel.clickEvent.observe(this) {
            when (it) {
                is NavigateToCocktailDetails -> navHomeToCocktailDetails(it.cocktail)
            }
        }
    }

    private fun handleSearchResult(it: State) {
        when {
            it.cocktail.isNotEmpty() -> {
                binding.searchResult.visibility = View.VISIBLE
                searchAdapter.updateItems(it.cocktail)
            }
            it.showError -> {
                searchAdapter.updateItems(emptyList())
                binding.searchResult.visibility = View.GONE
            }
        }
    }
}