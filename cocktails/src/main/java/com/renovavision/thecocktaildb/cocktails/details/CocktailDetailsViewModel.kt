package com.renovavision.thecocktaildb.cocktails.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.entities.CocktailInfoEntity.CocktailEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.domain.usecases.GetCocktails
import com.renovavision.thecocktaildb.ui.utils.Dispatchable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoadCocktailInfo(val drink: DrinkEntity) :
    Dispatchable

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val cocktailInfo: CocktailEntity? = null
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

    private fun loadCocktailInfo(cocktail: DrinkEntity) {
        getCocktailInfo.value = State(isLoading = true, showError = false)
        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            getCocktailInfo.value = State(isLoading = false, showError = true)
        }) {
            val cocktailInfo = getCocktails.loadCocktailDetails(cocktail.key)

            when (cocktailInfo.isEmpty()) {
                true -> getCocktailInfo.value = State(isLoading = false, showError = true)
                else -> getCocktailInfo.value = State(
                    isLoading = false,
                    showError = false,
                    cocktailInfo = cocktailInfo.first()
                )
            }
        }
    }
}