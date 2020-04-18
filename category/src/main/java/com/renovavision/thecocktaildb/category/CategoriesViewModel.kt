package com.renovavision.thecocktaildb.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.network.CocktailsApi
import com.renovavision.thecocktaildb.network.DrinksCategory
import com.renovavision.thecocktaildb.network.DrinksCategory.*
import com.renovavision.thecocktaildb.network.DrinksIngredient.Ingredient
import com.renovavision.thecocktaildb.utils.Dispatchable
import com.renovavision.thecocktaildb.utils.Event
import com.renovavision.thecocktaildb.utils.SingleLiveEvent
import com.renovavision.thecocktaildb.utils.ViewEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NavigateToCocktailsList(val category: Category) : ViewEvent

object LoadCategories : Event
data class CategoryClicked(val category: Category) : Event

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val categories: List<Category> = emptyList()
)

class CategoriesViewModel @Inject constructor(
    private val coctailsApi: CocktailsApi
) : ViewModel() {

    private val loadCategories = MutableLiveData<State>()
    private val actions = SingleLiveEvent<ViewEvent>()

    val state: LiveData<State>
        get() = loadCategories

    val clickEvent: LiveData<ViewEvent>
        get() = actions

    fun dispatch(dispatchable: Dispatchable) {
        when (dispatchable) {
            is LoadCategories -> loadIngredientsList()
            is CategoryClicked -> actions.value = NavigateToCocktailsList(dispatchable.category)
        }
    }

    private fun loadIngredientsList() {
        loadCategories.value = State(isLoading = true, showError = false)

        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            loadCategories.value = State(isLoading = false, showError = true)
        }) {
            val ingredients = coctailsApi.loadDrinksCategory()

            when (ingredients.drinks.isEmpty()) {
                true -> loadCategories.value = State(isLoading = false, showError = true)
                else -> loadCategories.value = State(
                    isLoading = false,
                    showError = false,
                    categories = ingredients.drinks
                )
            }
        }
    }
}