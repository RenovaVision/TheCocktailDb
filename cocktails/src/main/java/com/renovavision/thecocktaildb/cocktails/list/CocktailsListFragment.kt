package com.renovavision.thecocktaildb.cocktails.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renovavision.thecocktaildb.cocktails.R
import com.renovavision.thecocktaildb.cocktails.databinding.FragmentCocktailsListBinding
import com.renovavision.thecocktaildb.network.DrinksCategory
import com.renovavision.thecocktaildb.network.DrinksCategory.*
import com.renovavision.thecocktaildb.network.DrinksIngredient.Ingredient
import com.renovavision.thecocktaildb.utils.bindingDelegate
import com.renovavision.thecocktaildb.utils.observe
import com.renovavision.thecocktaildb.utils.onViewLifecycle
import javax.inject.Inject
import javax.inject.Named

class CocktailsListFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    @Named("navCocktailsListToDetails")
    private val navCocktailsListToDetails: (id: @JvmSuppressWildcards Int) -> Unit
) : Fragment(R.layout.fragment_cocktails_list) {

    private val viewModel: CocktailsListViewModel by viewModels { viewModelFactory }

    private val binding by bindingDelegate(FragmentCocktailsListBinding::bind)

    private val cocktailsAdapter = CocktailsAdapter { viewModel.dispatch(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ingredient = arguments?.getSerializable("ingredient")?.let { it as Ingredient }
        val category = arguments?.getSerializable("category")?.let { it as Category }

        onViewLifecycle({ binding.toolbar },
            {
                title = context.getString(R.string.cocktails)
            })
        onViewLifecycle({ binding.recyclerView },
            {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = cocktailsAdapter
            })
        onViewLifecycle({ binding.errorContainer },
            {
                errorMessage = getString(R.string.can_not_load_cocktails)
                clickListener =
                    View.OnClickListener { loadCocktailsList(ingredient, category) }
            }, {
                clickListener = null
            })

        loadCocktailsList(ingredient, category)
    }

    override fun onStart() {
        super.onStart()

        viewModel.state.observe(this) {
            cocktailsAdapter.updateItems(it.cocktails)
            binding.recyclerView.visibility = if (!it.showError) View.VISIBLE else View.GONE
            binding.errorContainer.visibility = if (it.showError) View.VISIBLE else View.GONE
            binding.progress.visibility = if (it.isLoading) View.VISIBLE else View.GONE
        }

        viewModel.clickEvent.observe(this) {
            when (it) {
                is NavigateToCocktailDetails -> navCocktailsListToDetails(it.id)
            }
        }
    }

    private fun loadCocktailsList(ingredient: Ingredient?, category: Category?) {
        when {
            ingredient != null -> viewModel.dispatch(LoadCocktailsByIngredient(ingredient))
            category != null -> viewModel.dispatch(LoadCocktailsByCategory(category))
            else -> binding.errorContainer.visibility = View.VISIBLE
        }
    }
}