package com.renovavision.thecocktaildb.cocktails.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.renovavision.thecocktaildb.cocktails.R
import com.renovavision.thecocktaildb.cocktails.databinding.FragmentCocktailDetailsBinding
import com.renovavision.thecocktaildb.domain.entities.Cocktail
import com.renovavision.thecocktaildb.ui.utils.bindingDelegate
import com.renovavision.thecocktaildb.ui.utils.observe
import com.renovavision.thecocktaildb.ui.utils.onViewLifecycle
import javax.inject.Inject

class CocktailDetailsFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_cocktail_details) {

    private val viewModel: CocktailDetailsViewModel by viewModels { viewModelFactory }

    private val binding by bindingDelegate(FragmentCocktailDetailsBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        val cocktail = arguments?.getSerializable("cocktail") as Cocktail

        onViewLifecycle({ binding.toolbar },
            {
                title = context.getString(R.string.cocktail_details)
            }
        )
        onViewLifecycle({ binding.cocktailInfoView }, {
            cocktailPoster = cocktail.strDrinkThumb
        })
        onViewLifecycle({ binding.errorContainer },
            {
                errorMessage = getString(R.string.can_not_load_cocktail_details)
                clickListener =
                    View.OnClickListener { viewModel.dispatch(LoadCocktailDetails(cocktail)) }
            }, {
                clickListener = null
            }
        )

        viewModel.dispatch(LoadCocktailDetails(cocktail))
    }

    override fun onStart() {
        super.onStart()

        viewModel.state.observe(this) {
            binding.cocktailInfoView.apply {
                visibility = if (!it.showError) View.VISIBLE else View.GONE
                it.cocktailDetails?.let { cocktailInfo ->
                    info = cocktailInfo
                }
            }
            binding.errorContainer.visibility = if (it.showError) View.VISIBLE else View.GONE
            binding.progress.visibility = if (it.isLoading) View.VISIBLE else View.GONE
        }
    }
}