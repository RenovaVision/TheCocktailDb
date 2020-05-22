package com.renovavision.thecocktaildb.categories

import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity.CategoryEntity
import com.renovavision.thecocktaildb.domain.usecases.GetCategoriesList
import com.renovavision.thecocktaildb.ui.dispatcher.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.ui.utils.Action
import com.renovavision.thecocktaildb.ui.utils.AsyncAction
import com.renovavision.thecocktaildb.ui.utils.UniViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

// Async actions
object LoadCategories : AsyncAction
data class CategoryClicked(val category: CategoryEntity) : AsyncAction

// Actions
object LoadCategoriesStarted : Action
object LoadCategoriesFailed : Action
data class LoadCategoriesSuccess(val categories: List<CategoryEntity>) : Action

// State
data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val categories: List<CategoryEntity> = emptyList()
)

// View model
@ExperimentalCoroutinesApi
class CategoriesViewModel @Inject constructor(
    private val getCategoriesList: GetCategoriesList,
    private val homeNavigator: CategoriesNavigator,
    provider: CoroutineDispatcherProvider
) : UniViewModel<State>(provider.ioDispatcher()) {

    override fun initState() = State(isLoading = true, showError = false)

    override fun reduce(state: State, action: Action): State =
        when (action) {
            is LoadCategoriesStarted -> state.copy(isLoading = true)
            is LoadCategoriesFailed -> state.copy(isLoading = false, showError = true)
            is LoadCategoriesSuccess -> state.copy(
                isLoading = false,
                showError = false,
                categories = action.categories
            )
            else -> state
        }

    override fun async(state: State, asyncAction: AsyncAction) {
        when (asyncAction) {
            is CategoryClicked -> homeNavigator.navCategoriesToCocktailsList(asyncAction.category)
            is LoadCategories -> {
                if (state.categories.isEmpty()) {
                    dispatch(LoadCategoriesStarted)
                    viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
                        dispatch(LoadCategoriesFailed)
                    }) {
                        val categories = getCategoriesList.invoke()
                        categories.collect {
                            when (it.isEmpty()) {
                                true -> dispatch(LoadCategoriesFailed)
                                else -> dispatch(LoadCategoriesSuccess(it))
                            }
                        }
                    }
                }
            }
        }
    }
}