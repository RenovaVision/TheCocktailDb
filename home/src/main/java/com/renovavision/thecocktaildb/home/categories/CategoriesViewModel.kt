package com.renovavision.thecocktaildb.home.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity.CategoryEntity
import com.renovavision.thecocktaildb.domain.usecases.GetCategoriesList
import com.renovavision.thecocktaildb.utils.Dispatchable
import com.renovavision.thecocktaildb.utils.Event
import com.renovavision.thecocktaildb.utils.SingleLiveEvent
import com.renovavision.thecocktaildb.utils.ViewEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NavigateToCocktailsList(val category: CategoryEntity) : ViewEvent

object LoadCategories : Event
data class CategoryClicked(val category: CategoryEntity) : Event

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val categories: List<CategoryEntity> = emptyList()
)

class CategoriesViewModel @Inject constructor(
    private val getCategoriesList: GetCategoriesList
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
            val ingredients = getCategoriesList.invoke()

            when (ingredients.isEmpty()) {
                true -> loadCategories.value = State(isLoading = false, showError = true)
                else -> loadCategories.value = State(
                    isLoading = false,
                    showError = false,
                    categories = ingredients
                )
            }
        }
    }
}