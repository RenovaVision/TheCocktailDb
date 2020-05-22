package com.renovavision.thecocktaildb.ingredients

import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.IngredientEntity
import com.renovavision.thecocktaildb.domain.usecases.GetIngredientsList
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.ui.utils.Action
import com.renovavision.thecocktaildb.ui.utils.AsyncAction
import com.renovavision.thecocktaildb.ui.utils.UniViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

object LoadIngredients : AsyncAction
data class IngredientClicked(val ingredient: IngredientEntity) : AsyncAction

object LoadIngredientsStarted : Action
object LoadIngredientsFailed : Action
data class LoadIngredientsSuccess(val ingredients: List<IngredientEntity>) : Action

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val ingredients: List<IngredientEntity> = emptyList()
)

@ExperimentalCoroutinesApi
class IngredientsViewModel @Inject constructor(
    private val getIngredientsList: GetIngredientsList,
    private val homeNavigator: IngredientsNavigator,
    provider: CoroutineDispatcherProvider
) : UniViewModel<State>(provider.ioDispatcher()) {

    override fun initState() = State(isLoading = true, showError = false)

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

    override fun async(state: State, asyncAction: AsyncAction) {
        when (asyncAction) {
            is IngredientClicked -> homeNavigator.navIngredientsToCocktailsList(asyncAction.ingredient)
            is LoadIngredients -> loadIngredients(state)
        }
    }

    private fun loadIngredients(state: State) {
        if (state.ingredients.isEmpty()) {
            dispatch(LoadIngredientsStarted)

            viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
                dispatch(LoadIngredientsFailed)
            }) {
                val ingredients = getIngredientsList.invoke()

                ingredients.collect {
                    when (it.isEmpty()) {
                        true -> dispatch(LoadIngredientsFailed)
                        else -> dispatch(LoadIngredientsSuccess(it))
                    }
                }
            }
        }
    }
}