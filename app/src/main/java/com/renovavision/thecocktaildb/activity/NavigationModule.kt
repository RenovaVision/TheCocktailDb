package com.renovavision.thecocktaildb.activity

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.renovavision.thecocktaildb.R
import com.renovavision.thecocktaildb.category.CategoriesFragmentDirections
import com.renovavision.thecocktaildb.cocktails.list.CocktailsListFragmentDirections
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity.CategoryEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.IngredientEntity
import com.renovavision.thecocktaildb.home.HomeFragmentDirections
import com.renovavision.thecocktaildb.ingredients.IngredientsFragmentDirections
import com.renovavision.thecocktaildb.inject.FragmentKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

@Module
internal object NavigationModule {

    @Provides
    @IntoMap
    @FragmentKey(NavHostFragment::class)
    fun navHostFragment(): Fragment = NavHostFragment()

    @Provides
    @Named("navHomeToCategoryList")
    fun navHomeToCategoriesList(mainActivity: MainActivity): () -> Unit = {
        mainActivity.findNavController(R.id.navHostFragment).navigate(
            HomeFragmentDirections.navigateToCategories()
        )
    }

    @Provides
    @Named("navHomeToIngredientList")
    fun navHomeToIngredientsList(mainActivity: MainActivity): () -> Unit = {
        mainActivity.findNavController(R.id.navHostFragment).navigate(
            HomeFragmentDirections.navigateToIngredients()
        )
    }

    @Provides
    @Named("navHomeToCocktailDetails")
    fun navHomeToCocktailDetails(mainActivity: MainActivity): (cocktail: DrinkEntity) -> Unit = {
        mainActivity.findNavController(R.id.navHostFragment).navigate(
            HomeFragmentDirections.navigateToCocktailDetails(it)
        )
    }

    @Provides
    @Named("navIngredientsToCocktailsList")
    fun navIngredientsToCocktailsList(mainActivity: MainActivity): (ingredient: IngredientEntity) -> Unit =
        {
            mainActivity.findNavController(R.id.navHostFragment).navigate(
                IngredientsFragmentDirections.navigateToCocktailsList(
                    ingredient = it,
                    category = null
                )
            )
        }

    @Provides
    @Named("navCategoriesToCocktailsList")
    fun navCategoriesToCocktailsList(mainActivity: MainActivity): (category: CategoryEntity) -> Unit =
        {
            mainActivity.findNavController(R.id.navHostFragment).navigate(
                CategoriesFragmentDirections.navigateToCocktailsList(
                    ingredient = null,
                    category = it
                )
            )
        }

    @Provides
    @Named("navCocktailsListToDetails")
    fun navCocktailsListToDetails(mainActivity: MainActivity): (cocktail: DrinkEntity) -> Unit = {
        mainActivity.findNavController(R.id.navHostFragment).navigate(
            CocktailsListFragmentDirections.navigateToCocktailDetails(it)
        )
    }

    @Provides
    @Named("navBack")
    fun navBack(mainActivity: MainActivity): () -> Boolean = {
        mainActivity.findNavController(R.id.navHostFragment).popBackStack()
    }
}