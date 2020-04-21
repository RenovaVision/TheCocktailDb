package com.renovavision.thecocktaildb.cocktails.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.cocktails.CocktailsUseCase
import com.renovavision.thecocktaildb.network.CocktailsApi
import com.renovavision.thecocktaildb.network.DrinksByQuery
import com.renovavision.thecocktaildb.network.DrinksByQuery.Drink
import com.renovavision.thecocktaildb.network.DrinksCategory
import com.renovavision.thecocktaildb.network.DrinksCategory.*
import com.renovavision.thecocktaildb.network.DrinksIngredient.*
import com.renovavision.thecocktaildb.utils.Dispatchable
import com.renovavision.thecocktaildb.utils.Event
import com.renovavision.thecocktaildb.utils.SingleLiveEvent
import com.renovavision.thecocktaildb.utils.ViewEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NavigateToCocktailDetails(val cocktail: Drink) : ViewEvent

data class LoadCocktailsByIngredient(val ingredient: Ingredient) : Event
data class LoadCocktailsByCategory(val category: Category) : Event
data class CocktailClicked(val cocktail: Drink) : Event

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val cocktails: List<Drink> = emptyList()
)

class CocktailsListViewModel @Inject constructor(
    private val useCase: CocktailsUseCase
) : ViewModel() {

    private val loadCocktails = MutableLiveData<State>()
    private val actions = SingleLiveEvent<ViewEvent>()

    val state: LiveData<State>
        get() = loadCocktails

    val clickEvent: LiveData<ViewEvent>
        get() = actions

    fun dispatch(dispatchable: Dispatchable) {
        when (dispatchable) {
            is LoadCocktailsByIngredient -> loadCocktailsListByIngredient(dispatchable.ingredient)
            is LoadCocktailsByCategory -> loadCocktailsListByCategory(dispatchable.category)
            is CocktailClicked -> actions.value = NavigateToCocktailDetails(dispatchable.cocktail)
        }
    }

    private fun loadCocktailsListByIngredient(ingredient: Ingredient) {
        loadCocktails.value = State(isLoading = true, showError = false)

        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            loadCocktails.value = State(isLoading = false, showError = true)
        }) {
            val cocktails = useCase.invoke(ingredient) as DrinksByQuery

            when (cocktails.drinks.isEmpty()) {
                true -> loadCocktails.value = State(isLoading = false, showError = true)
                else -> loadCocktails.value =
                    State(isLoading = false, showError = false, cocktails = cocktails.drinks)
            }
        }
    }

    private fun loadCocktailsListByCategory(category: Category) {
        loadCocktails.value = State(isLoading = true, showError = false)

        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            loadCocktails.value = State(isLoading = false, showError = true)
        }) {
            val cocktails = useCase.invoke(category) as DrinksByQuery

            when (cocktails.drinks.isEmpty()) {
                true -> loadCocktails.value = State(isLoading = false, showError = true)
                else -> loadCocktails.value =
                    State(isLoading = false, showError = false, cocktails = cocktails.drinks)
            }
        }
    }
}