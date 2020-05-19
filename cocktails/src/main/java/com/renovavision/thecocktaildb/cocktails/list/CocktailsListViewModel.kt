package com.renovavision.thecocktaildb.cocktails.list

import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.cocktails.CocktailsNavigator
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity.CategoryEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.IngredientEntity
import com.renovavision.thecocktaildb.domain.usecases.GetCocktails
import com.renovavision.thecocktaildb.ui.utils.Action
import com.renovavision.thecocktaildb.ui.utils.AsyncAction
import com.renovavision.thecocktaildb.ui.utils.UniViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

data class LoadCocktailsByIngredient(val ingredient: IngredientEntity) : AsyncAction
data class LoadCocktailsByCategory(val category: CategoryEntity) : AsyncAction
class CocktailClicked(
    val cocktail: DrinkEntity,
    val imageView: WeakReference<AppCompatImageView>
) : AsyncAction

object LoadCocktailsStarted : Action
object LoadCocktailsFailed : Action
data class LoadCocktailsSuccess(val cocktails: List<DrinkEntity>) : Action

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val cocktails: List<DrinkEntity> = emptyList()
)

class CocktailsListViewModel @Inject constructor(
    private val getCocktails: GetCocktails,
    private val cocktailsNavigator: CocktailsNavigator
) : UniViewModel<State>() {

    override fun initState() = State(isLoading = true, showError = false)

    override fun reduce(state: State, action: Action): State =
        when (action) {
            is LoadCocktailsStarted -> state.copy(isLoading = true)
            is LoadCocktailsFailed -> state.copy(isLoading = false, showError = true)
            is LoadCocktailsSuccess -> state.copy(isLoading = false, cocktails = action.cocktails)
            else -> state
        }

    override fun async(state: State, asyncAction: AsyncAction) {
        when (asyncAction) {
            is CocktailClicked -> cocktailsNavigator.navCocktailsListToDetails(asyncAction.cocktail, asyncAction.imageView)
            is LoadCocktailsByIngredient -> loadCocktailsListByIngredient(
                state,
                asyncAction.ingredient
            )
            is LoadCocktailsByCategory -> loadCocktailsListByCategory(state, asyncAction.category)
        }
    }

    private fun loadCocktailsListByIngredient(state: State, ingredient: IngredientEntity) {
        if (state.cocktails.isEmpty()) {
            dispatch(LoadCocktailsStarted)

            viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
                dispatch(LoadCocktailsFailed)
            }) {
                val cocktails = getCocktails.loadCocktailsListByIngredient(ingredient.key)

                when (cocktails.isEmpty()) {
                    true -> dispatch(LoadCocktailsFailed)
                    else -> dispatch(LoadCocktailsSuccess(cocktails))
                }
            }
        }
    }

    private fun loadCocktailsListByCategory(state: State, category: CategoryEntity) {
        if (state.cocktails.isEmpty()) {
            dispatch(LoadCocktailsStarted)

            viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
                dispatch(LoadCocktailsFailed)
            }) {
                val cocktails = getCocktails.loadCocktailsListByCategory(category.key)

                when (cocktails.isEmpty()) {
                    true -> dispatch(LoadCocktailsFailed)
                    else -> dispatch(LoadCocktailsSuccess(cocktails))
                }
            }
        }
    }
}