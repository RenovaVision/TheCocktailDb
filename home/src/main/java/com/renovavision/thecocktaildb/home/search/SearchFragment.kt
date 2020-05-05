package com.renovavision.thecocktaildb.home.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.home.R
import com.renovavision.thecocktaildb.home.databinding.FragmentSearchBinding
import com.renovavision.thecocktaildb.utils.bindingDelegate
import com.renovavision.thecocktaildb.utils.observe
import com.renovavision.thecocktaildb.utils.onViewLifecycle
import javax.inject.Inject
import javax.inject.Named

class SearchFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    @Named("navSearchToCocktailDetails")
    private val navSearchToCocktailDetails: (cocktail: @JvmSuppressWildcards DrinkEntity) -> Unit
): Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels { viewModelFactory }

    private val binding by bindingDelegate(FragmentSearchBinding::bind)

    private val searchAdapter =
        SearchResultAdapter {
            viewModel.dispatch(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onViewLifecycle({ binding.searchResult },
            {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = searchAdapter
            })

        onViewLifecycle({ binding.searchText },
            {
                doAfterTextChanged { editable ->
                    viewModel.dispatch(LoadCocktails(editable.toString()))
                }
            })
    }

    override fun onStart() {
        super.onStart()

        viewModel.state.observe(this) {
            searchAdapter.updateItems(it.cocktail)
            binding.searchResult.visibility = if (!it.showError) View.VISIBLE else View.GONE
            binding.emptyResult.visibility = if (it.showError) View.VISIBLE else View.GONE
            binding.progress.visibility = if (it.isLoading) View.VISIBLE else View.GONE
        }

        viewModel.clickEvent.observe(this) {
            when (it) {
                is NavigateToCocktailDetails -> navSearchToCocktailDetails(it.cocktail)
            }
        }
    }
}