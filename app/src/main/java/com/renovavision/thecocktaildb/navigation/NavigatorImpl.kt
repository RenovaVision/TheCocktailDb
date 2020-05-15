package com.renovavision.thecocktaildb.navigation

import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.renovavision.thecocktaildb.R
import com.renovavision.thecocktaildb.activity.MainActivity
import com.renovavision.thecocktaildb.cocktails.CocktailsNavigator
import com.renovavision.thecocktaildb.cocktails.list.CocktailsListFragmentDirections
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity
import com.renovavision.thecocktaildb.home.HomeFragmentDirections
import com.renovavision.thecocktaildb.home.HomeNavigator
import com.renovavision.thecocktaildb.home.categories.CategoriesFragmentDirections
import com.renovavision.thecocktaildb.home.ingredients.IngredientsFragmentDirections
import com.renovavision.thecocktaildb.home.search.SearchFragmentDirections
import com.renovavision.thecocktaildb.ui.navigation.Navigator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigatorImpl @Inject constructor() : Navigator, CocktailsNavigator, HomeNavigator {

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

    override fun navCocktailsListToDetails(cocktail: DrinksByQueryEntity.DrinkEntity) {
        activity?.apply {
            runOnUiThread {
                findNavController(R.id.navHostFragment).navigate(
                    CocktailsListFragmentDirections.navigateToCocktailDetails(cocktail),
                    FragmentNavigatorExtras()
                )
            }
        }
    }

    override fun navHomeToSearch() {
        activity?.apply {
            runOnUiThread {
                findNavController(R.id.navHostFragment).navigate(
                    HomeFragmentDirections.navigateToSearch()
                )
            }
        }
    }

    override fun navIngredientsToCocktailsList(ingredient: DrinksIngredientEntity.IngredientEntity) {
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

    override fun navCategoriesToCocktailsList(category: DrinksCategoryEntity.CategoryEntity) {
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

    override fun navSearchToCocktailDetails(cocktail: DrinksByQueryEntity.DrinkEntity) {
        activity?.apply {
            runOnUiThread {
                findNavController(R.id.navHostFragment).navigate(
                    SearchFragmentDirections.navigateToCocktailDetails(cocktail)
                )
            }
        }
    }

}