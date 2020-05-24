package com.renovavision.thecocktaildb.ingredients

import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.usecases.GetIngredientsList
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.entities.Ingredient
import com.renovavision.thecocktaildb.ui.uni.Action
import com.renovavision.thecocktaildb.ui.uni.AsyncAction
import com.renovavision.thecocktaildb.ui.uni.UniViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

object LoadIngredients : AsyncAction
data class IngredientClicked(val ingredient: Ingredient) :
    AsyncAction

object LoadIngredientsStarted : Action
object LoadIngredientsFailed : Action
data class LoadIngredientsSuccess(val ingredients: List<Ingredient>) :
    Action

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val ingredients: List<Ingredient> = emptyList()
)

class IngredientsViewModel @Inject constructor(
    private val getIngredientsList: GetIngredientsList,
    private val homeNavigator: IngredientsNavigator,
    provider: CoroutineDispatcherProvider
) : UniViewModel<State>(provider.ioDispatcher()) {

    @ExperimentalCoroutinesApi
    override fun getDefaultState() = State(isLoading = true, showError = false)

    override fun reduce(state: State, action: Action): State =
        when (action) {
            is LoadIngredientsStarted -> state.copy(isLoading = true)
            is LoadIngredientsFailed -> state.copy(isLoading = false, showError = true)
            is LoadIngredientsSuccess -> state.copy(
                isLoading = false,
                showError = false,
                ingredients = action.ingredients
            )
            else -> state
        }

    @ExperimentalCoroutinesApi
    override fun async(state: State, asyncAction: AsyncAction) {
        when (asyncAction) {
            is IngredientClicked -> homeNavigator.navIngredientsToCocktailsList(asyncAction.ingredient)
            is LoadIngredients -> if (state.ingredients.isEmpty()) {
                dispatch(LoadIngredientsStarted)
                viewModelScope.launch {
                    getIngredientsList.invoke()
                        .catch { dispatch(LoadIngredientsFailed) }
                        .collect {
                            when (it.isEmpty()) {
                                true -> dispatch(LoadIngredientsStarted)
                                false -> dispatch(LoadIngredientsSuccess(it))
                            }
                        }
                }
            }
        }
    }
}