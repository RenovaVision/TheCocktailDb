package com.renovavision.thecocktaildb.cocktails.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity.CategoryEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.IngredientEntity
import com.renovavision.thecocktaildb.domain.usecases.GetCocktails
import com.renovavision.thecocktaildb.ui.CocktailView
import com.renovavision.thecocktaildb.utils.Dispatchable
import com.renovavision.thecocktaildb.utils.Event
import com.renovavision.thecocktaildb.utils.SingleLiveEvent
import com.renovavision.thecocktaildb.utils.ViewEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NavigateToCocktailDetails(val cocktail: DrinkEntity) : ViewEvent

data class LoadCocktailsByIngredient(val ingredient: IngredientEntity) : Event
data class LoadCocktailsByCategory(val category: CategoryEntity) : Event
data class CocktailClicked(val cocktail: DrinkEntity) : Event

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val cocktails: List<DrinkEntity> = emptyList()
)

class CocktailsListViewModel @Inject constructor(
    private val getCocktails: GetCocktails
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

    private fun loadCocktailsListByIngredient(ingredient: IngredientEntity) {
        loadCocktails.value = State(isLoading = true, showError = false)

        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            loadCocktails.value = State(isLoading = false, showError = true)
        }) {
            val cocktails = getCocktails.loadCocktailsListByIngredient(ingredient.key)

            when (cocktails.isEmpty()) {
                true -> loadCocktails.value = State(isLoading = false, showError = true)
                else -> loadCocktails.value =
                    State(isLoading = false, showError = false, cocktails = cocktails)
            }
        }
    }

    private fun loadCocktailsListByCategory(category: CategoryEntity) {
        loadCocktails.value = State(isLoading = true, showError = false)

        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            loadCocktails.value = State(isLoading = false, showError = true)
        }) {
            val cocktails = getCocktails.loadCocktailsListByCategory(category.key)

            when (cocktails.isEmpty()) {
                true -> loadCocktails.value = State(isLoading = false, showError = true)
                else -> loadCocktails.value =
                    State(isLoading = false, showError = false, cocktails = cocktails)
            }
        }
    }
}