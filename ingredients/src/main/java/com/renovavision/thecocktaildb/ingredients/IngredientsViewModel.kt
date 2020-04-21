package com.renovavision.thecocktaildb.ingredients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.network.CocktailsApi
import com.renovavision.thecocktaildb.network.DrinksIngredient.Ingredient
import com.renovavision.thecocktaildb.utils.Dispatchable
import com.renovavision.thecocktaildb.utils.Event
import com.renovavision.thecocktaildb.utils.SingleLiveEvent
import com.renovavision.thecocktaildb.utils.ViewEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NavigateToCocktailsList(val ingredient: Ingredient) : ViewEvent

object LoadIngredients : Event
data class IngredientClicked(val ingredient: Ingredient) : Event

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val ingredients: List<Ingredient> = emptyList()
)

class IngredientsViewModel @Inject constructor(
    private val useCase: IngredientsUseCase
) : ViewModel() {

    private val loadIngredients = MutableLiveData<State>()
    private val actions = SingleLiveEvent<ViewEvent>()

    val state: LiveData<State>
        get() = loadIngredients

    val clickEvent: LiveData<ViewEvent>
        get() = actions

    fun dispatch(dispatchable: Dispatchable) {
        when (dispatchable) {
            is LoadIngredients -> loadIngredientsList()
            is IngredientClicked -> actions.value = NavigateToCocktailsList(dispatchable.ingredient)
        }
    }

    private fun loadIngredientsList() {
        loadIngredients.value = State(isLoading = true, showError = false)

        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            loadIngredients.value = State(isLoading = false, showError = true)
        }) {
            val ingredients = useCase.invoke()

            when (ingredients.drinks.isEmpty()) {
                true -> loadIngredients.value = State(isLoading = false, showError = true)
                else -> loadIngredients.value = State(
                    isLoading = false,
                    showError = false,
                    ingredients = ingredients.drinks
                )
            }
        }
    }
}