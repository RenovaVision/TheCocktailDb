package com.renovavision.thecocktaildb.ingredients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.IngredientEntity
import com.renovavision.thecocktaildb.domain.usecases.GetIngredientsList
import com.renovavision.thecocktaildb.ui.utils.Dispatchable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

object LoadIngredients : Dispatchable
data class IngredientClicked(val ingredient: IngredientEntity) :
    Dispatchable

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val ingredients: List<IngredientEntity> = emptyList()
)

class IngredientsViewModel @Inject constructor(
    private val getIngredientsList: GetIngredientsList,
    private val homeNavigator: IngredientsNavigator
) : ViewModel() {

    private val loadIngredients = MutableLiveData<State>()

    val state: LiveData<State>
        get() = loadIngredients

    fun dispatch(dispatchable: Dispatchable) {
        when (dispatchable) {
            is LoadIngredients -> loadIngredientsList()
            is IngredientClicked -> homeNavigator.navIngredientsToCocktailsList(dispatchable.ingredient)
        }
    }

    private fun loadIngredientsList() {
        loadIngredients.value = State(
            isLoading = true,
            showError = false
        )

        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            loadIngredients.value =
                State(
                    isLoading = false,
                    showError = true
                )
        }) {
            val ingredients = getIngredientsList.invoke()

            when (ingredients.isEmpty()) {
                true -> loadIngredients.value =
                    State(
                        isLoading = false,
                        showError = true
                    )
                else -> loadIngredients.value =
                    State(
                        isLoading = false,
                        showError = false,
                        ingredients = ingredients
                    )
            }
        }
    }
}