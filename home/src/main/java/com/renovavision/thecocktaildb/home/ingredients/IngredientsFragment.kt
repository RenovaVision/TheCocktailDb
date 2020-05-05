package com.renovavision.thecocktaildb.home.ingredients

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.IngredientEntity
import com.renovavision.thecocktaildb.home.R
import com.renovavision.thecocktaildb.home.databinding.FragmentIngredientsListBinding
import com.renovavision.thecocktaildb.utils.bindingDelegate
import com.renovavision.thecocktaildb.utils.observe
import com.renovavision.thecocktaildb.utils.onViewLifecycle
import javax.inject.Inject
import javax.inject.Named

class IngredientsFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    @Named("navIngredientsToCocktailsList")
    private val navIngredientsToCocktailsList: (ingredient: @JvmSuppressWildcards IngredientEntity) -> Unit
) : Fragment(R.layout.fragment_ingredients_list) {

    private val viewModel: IngredientsViewModel by viewModels { viewModelFactory }

    private val binding by bindingDelegate(FragmentIngredientsListBinding::bind)

    private val ingredientsAdapter =
        IngredientsAdapter {
            viewModel.dispatch(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onViewLifecycle({ binding.recyclerView },
            {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = ingredientsAdapter
            })
        onViewLifecycle({ binding.errorContainer },
            {
                errorMessage = getString(R.string.can_not_load_ingredients)
                clickListener = View.OnClickListener { viewModel.dispatch(LoadIngredients) }
            }, {
                clickListener = null
            })

        viewModel.dispatch(LoadIngredients)
    }

    override fun onStart() {
        super.onStart()

        viewModel.state.observe(this) {
            ingredientsAdapter.updateItems(it.ingredients)
            binding.recyclerView.visibility = if (!it.showError) View.VISIBLE else View.GONE
            binding.errorContainer.visibility = if (it.showError) View.VISIBLE else View.GONE
            binding.progress.visibility = if (it.isLoading) View.VISIBLE else View.GONE
        }

        viewModel.clickEvent.observe(this) {
            when (it) {
                is NavigateToCocktailsList -> {
                    navIngredientsToCocktailsList(it.ingredient)
                }
            }
        }
    }
}