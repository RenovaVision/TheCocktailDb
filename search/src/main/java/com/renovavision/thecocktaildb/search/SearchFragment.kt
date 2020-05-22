package com.renovavision.thecocktaildb.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renovavision.thecocktaildb.search.databinding.FragmentSearchBinding
import com.renovavision.thecocktaildb.ui.utils.bindingDelegate
import com.renovavision.thecocktaildb.ui.utils.observe
import com.renovavision.thecocktaildb.ui.utils.onViewLifecycle
import javax.inject.Inject

class SearchFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels { viewModelFactory }

    private val binding by bindingDelegate(FragmentSearchBinding::bind)

    private val searchAdapter =
        SearchResultAdapter {
            viewModel.dispatch(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onViewLifecycle({ binding.searchText },
            {
                doAfterTextChanged { editable ->
                    viewModel.dispatch(LoadCocktails(editable.toString()))
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.searchResult).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = searchAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.state.observe(this) {
            searchAdapter.updateItems(it.cocktails)
            binding.searchResult.visibility = if (!it.showError) View.VISIBLE else View.GONE
            binding.emptyResult.visibility = if (it.showError) View.VISIBLE else View.GONE
            binding.progress.visibility = if (it.isLoading) View.VISIBLE else View.GONE
        }
    }
}