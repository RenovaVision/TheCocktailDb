package com.renovavision.thecocktaildb.activity

import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.renovavision.thecocktaildb.R
import com.renovavision.thecocktaildb.categories.CategoriesFragmentDirections
import com.renovavision.thecocktaildb.cocktails.CocktailsNavigator
import com.renovavision.thecocktaildb.cocktails.list.CocktailsListFragmentDirections
import com.renovavision.thecocktaildb.categories.CategoriesNavigator
import com.renovavision.thecocktaildb.domain.entities.Category
import com.renovavision.thecocktaildb.domain.entities.Cocktail
import com.renovavision.thecocktaildb.domain.entities.Ingredient
import com.renovavision.thecocktaildb.ingredients.IngredientsFragmentDirections
import com.renovavision.thecocktaildb.ingredients.IngredientsNavigator
import com.renovavision.thecocktaildb.ui.navigation.Navigator
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigatorImpl @Inject constructor() : Navigator, CocktailsNavigator,
    CategoriesNavigator, IngredientsNavigator {

    private var activity: MainActivity? = null

    fun bind(mainActivity: MainActivity) {
        this.activity = mainActivity
    }

    fun unbind() {
        this.activity = null
    }

    override fun navBack() {
        activity?.apply {
            runOnUiThread {
                findNavController(R.id.navHostFragment).popBackStack()
            }
        }
    }

    override fun navCocktailsListToDetails(
        cocktail: Cocktail,
        imageView: WeakReference<AppCompatImageView>
    ) {
        activity?.apply {
            runOnUiThread {
                val extras =
                    imageView.get()?.let { FragmentNavigatorExtras(it to cocktail.strDrinkThumb) }

                extras?.let {
                    findNavController(R.id.navHostFragment).navigate(
                        CocktailsListFragmentDirections.navigateToCocktailDetails(cocktail),
                        it
                    )
                }
            }
        }
    }

    override fun navIngredientsToCocktailsList(ingredient: Ingredient) {
        activity?.apply {
            runOnUiThread {
                findNavController(R.id.navHostFragment).navigate(
                    IngredientsFragmentDirections.navigateToCocktailsList(
                        ingredient = ingredient,
                        category = null
                    )
                )
            }
        }
    }

    override fun navCategoriesToCocktailsList(category: Category) {
        activity?.apply {
            runOnUiThread {
                findNavController(R.id.navHostFragment).navigate(
                    CategoriesFragmentDirections.navigateToCocktailsList(
                        ingredient = null,
                        category = category
                    )
                )
            }
        }
    }

}