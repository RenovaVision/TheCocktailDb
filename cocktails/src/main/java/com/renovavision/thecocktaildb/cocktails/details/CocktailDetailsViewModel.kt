package com.renovavision.thecocktaildb.cocktails.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.cocktails.GetCocktails
import com.renovavision.thecocktaildb.network.CocktailInfo.Cocktail
import com.renovavision.thecocktaildb.network.DrinksByQuery.*
import com.renovavision.thecocktaildb.utils.Dispatchable
import com.renovavision.thecocktaildb.utils.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoadCocktailInfo(val drink: Drink) : Event

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val cocktailInfo: Cocktail? = null
)

class CocktailDetailsViewModel @Inject constructor(
    private val getCocktails: GetCocktails
) : ViewModel() {

    private val getCocktailInfo = MutableLiveData<State>()
    val state: LiveData<State>
        get() = getCocktailInfo

    fun dispatch(dispatchable: Dispatchable) {
        when (dispatchable) {
            is LoadCocktailInfo -> loadCocktailInfo(dispatchable.drink)
        }
    }

    private fun loadCocktailInfo(cocktail: Drink) {
        getCocktailInfo.value = State(isLoading = true, showError = false)

        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            getCocktailInfo.value = State(isLoading = false, showError = true)
        }) {
            val cocktailInfo = getCocktails.loadCocktailDetails(cocktail.key)

            when (cocktailInfo.drinks.isEmpty()) {
                true -> getCocktailInfo.value = State(isLoading = false, showError = true)
                else -> getCocktailInfo.value = State(
                    isLoading = false,
                    showError = false,
                    cocktailInfo = cocktailInfo.drinks.first()
                )
            }
        }
    }
}