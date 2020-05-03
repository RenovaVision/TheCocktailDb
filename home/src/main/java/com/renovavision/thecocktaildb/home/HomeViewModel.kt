package com.renovavision.thecocktaildb.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.network.DrinksByQuery.Drink
import com.renovavision.thecocktaildb.utils.Dispatchable
import com.renovavision.thecocktaildb.utils.Event
import com.renovavision.thecocktaildb.utils.SingleLiveEvent
import com.renovavision.thecocktaildb.utils.ViewEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NavigateToCocktailDetails(val cocktail: Drink) : ViewEvent

data class LoadCocktails(val query: String) : Event
data class CocktailClicked(val cocktail: Drink) : Event

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val cocktail: List<Drink> = emptyList()
)

class HomeViewModel @Inject constructor(private val useCase: SearchCocktails) : ViewModel() {

    private val loadCocktails = MutableLiveData<State>()
    private val actions = SingleLiveEvent<ViewEvent>()

    val state: LiveData<State>
        get() = loadCocktails

    val clickEvent: LiveData<ViewEvent>
        get() = actions

    fun dispatch(dispatchable: Dispatchable) {
        when (dispatchable) {
            is LoadCocktails -> loadCocktailsInfo(dispatchable.query)
            is CocktailClicked -> actions.value = NavigateToCocktailDetails(dispatchable.cocktail)
        }
    }

    private fun loadCocktailsInfo(query: String) {
        loadCocktails.value = State(isLoading = true, showError = false)

        when {
            query.length < 3 -> {
                loadCocktails.value = State(isLoading = false, showError = true)
            }
            else -> {
                viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
                    loadCocktails.value = State(isLoading = false, showError = true)
                }) {
                    val cocktails = useCase.invoke(query)

                    when (cocktails.isEmpty()) {
                        true -> loadCocktails.value = State(isLoading = false, showError = true)
                        else -> loadCocktails.value = State(
                            isLoading = false,
                            showError = false,
                            cocktail = cocktails
                        )
                    }
                }
            }
        }
    }
}