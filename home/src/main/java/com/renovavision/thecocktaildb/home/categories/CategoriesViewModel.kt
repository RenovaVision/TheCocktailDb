package com.renovavision.thecocktaildb.home.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity.CategoryEntity
import com.renovavision.thecocktaildb.domain.usecases.GetCategoriesList
import com.renovavision.thecocktaildb.home.HomeNavigator
import com.renovavision.thecocktaildb.utils.Dispatchable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

object LoadCategories : Dispatchable
data class CategoryClicked(val category: CategoryEntity) : Dispatchable

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val categories: List<CategoryEntity> = emptyList()
)

class CategoriesViewModel @Inject constructor(
    private val getCategoriesList: GetCategoriesList,
    private val homeNavigator: HomeNavigator
) : ViewModel() {

    private val loadCategories = MutableLiveData<State>()

    val state: LiveData<State>
        get() = loadCategories

    fun dispatch(dispatchable: Dispatchable) {
        when (dispatchable) {
            is LoadCategories -> loadIngredientsList()
            is CategoryClicked -> homeNavigator.navCategoriesToCocktailsList(dispatchable.category)
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