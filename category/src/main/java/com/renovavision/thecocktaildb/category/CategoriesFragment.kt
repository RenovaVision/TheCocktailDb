package com.renovavision.thecocktaildb.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renovavision.thecocktaildb.category.databinding.FragmentCategoryListBinding
import com.renovavision.thecocktaildb.network.DrinksCategory
import com.renovavision.thecocktaildb.network.DrinksCategory.*
import com.renovavision.thecocktaildb.utils.bindingDelegate
import com.renovavision.thecocktaildb.utils.observe
import com.renovavision.thecocktaildb.utils.onViewLifecycle
import javax.inject.Inject
import javax.inject.Named

class CategoriesFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    @Named("navCategoriesToCocktailsList")
    private val navCategoriesToCocktailsList: (category: @JvmSuppressWildcards Category) -> Unit
) : Fragment(R.layout.fragment_category_list) {

    private val viewModel: CategoriesViewModel by viewModels { viewModelFactory }

    private val binding by bindingDelegate(FragmentCategoryListBinding::bind)

    private val ingredientsAdapter = CategoriesAdapter { viewModel.dispatch(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        onViewLifecycle({ binding.toolbar },
            {
                title = context.getString(R.string.categories)
            })
        onViewLifecycle({ binding.recyclerView },
            {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = ingredientsAdapter
            })
        onViewLifecycle({ binding.errorContainer },
            {
                errorMessage = getString(R.string.can_not_load_categories)
                clickListener = View.OnClickListener { viewModel.dispatch(LoadCategories) }
            }, {
                clickListener = null
            })

        viewModel.dispatch(LoadCategories)
    }

    override fun onStart() {
        super.onStart()

        viewModel.state.observe(this) {
            ingredientsAdapter.updateItems(it.categories)
            binding.recyclerView.visibility = if (!it.showError) View.VISIBLE else View.GONE
            binding.errorContainer.visibility = if (it.showError) View.VISIBLE else View.GONE
            binding.progress.visibility = if (it.isLoading) View.VISIBLE else View.GONE
        }

        viewModel.clickEvent.observe(this) {
            when (it) {
                is NavigateToCocktailsList -> {
                    navCategoriesToCocktailsList(it.category)
                }
            }
        }
    }
}