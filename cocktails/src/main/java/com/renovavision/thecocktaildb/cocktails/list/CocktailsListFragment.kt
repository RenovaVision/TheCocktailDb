package com.renovavision.thecocktaildb.cocktails.list

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renovavision.thecocktaildb.cocktails.R
import com.renovavision.thecocktaildb.cocktails.databinding.FragmentCocktailsListBinding
import com.renovavision.thecocktaildb.domain.entities.Category
import com.renovavision.thecocktaildb.domain.entities.Ingredient
import com.renovavision.thecocktaildb.ui.utils.bindingDelegate
import com.renovavision.thecocktaildb.ui.utils.observe
import com.renovavision.thecocktaildb.ui.utils.onViewLifecycle
import javax.inject.Inject

class CocktailsListFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = cocktailsAdapter

            postponeEnterTransition()
            doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.state.observe(this) {
            cocktailsAdapter.updateItems(it.cocktails)
            binding.recyclerView.visibility = if (!it.showError) View.VISIBLE else View.GONE
            binding.errorContainer.visibility = if (it.showError) View.VISIBLE else View.GONE
            binding.progress.visibility = if (it.isLoading) View.VISIBLE else View.GONE
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