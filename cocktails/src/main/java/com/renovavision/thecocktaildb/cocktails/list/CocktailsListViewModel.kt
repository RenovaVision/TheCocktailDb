package com.renovavision.thecocktaildb.cocktails.list

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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoadCocktailsByIngredient(val ingredient: IngredientEntity) : AsyncAction
data class LoadCocktailsByCategory(val category: CategoryEntity) : AsyncAction
data class CocktailClicked(val cocktail: DrinkEntity) : AsyncAction

object LoadCocktailsStarted : Action
object LoadCocktailsFailed : Action
data class LoadCocktailsSuccess(val cocktails: List<DrinkEntity>) : Action

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val cocktails: List<DrinkEntity> = emptyList()
)

@ExperimentalCoroutinesApi
class CocktailsListViewModel @Inject constructor(
    private val getCocktails: GetCocktails,
    private val cocktailsNavigator: CocktailsNavigator
) : UniViewModel<State>() {

    override fun initState() = State(isLoading = true, showError = false)

    override fun reduce(state: State, action: Action): State =
        when (action) {
            is LoadCocktailsStarted -> state.copy(isLoading = true)
            is LoadCocktailsFailed -> state.copy(isLoading = false, showError = true)
            is LoadCocktailsSuccess -> state.copy(
                isLoading = false,
                showError = false,
                cocktails = action.cocktails
            )
            else -> state
        }

    override fun async(state: State, asyncAction: AsyncAction) {
        when (asyncAction) {
            is CocktailClicked -> cocktailsNavigator.navCocktailsListToDetails(asyncAction.cocktail)
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

                cocktails.collect {
                    when (it.isEmpty()) {
                        true -> dispatch(LoadCocktailsFailed)
                        else -> dispatch(LoadCocktailsSuccess(it))
                    }
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

                cocktails.collect {
                    when (it.isEmpty()) {
                        true -> dispatch(LoadCocktailsFailed)
                        else -> dispatch(LoadCocktailsSuccess(it))
                    }
                }
            }
        }
    }
}