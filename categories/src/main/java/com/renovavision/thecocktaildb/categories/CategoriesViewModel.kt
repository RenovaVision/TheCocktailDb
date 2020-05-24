package com.renovavision.thecocktaildb.categories

import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.usecases.GetCategoriesList
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.entities.Category
import com.renovavision.thecocktaildb.ui.uni.Action
import com.renovavision.thecocktaildb.ui.uni.AsyncAction
import com.renovavision.thecocktaildb.ui.uni.UniViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

// Async actions
object LoadCategories : AsyncAction
data class CategoryClicked(val category: Category) :
    AsyncAction

// Actions
object LoadCategoriesStarted : Action
object LoadCategoriesFailed : Action
data class LoadCategoriesSuccess(val categories: List<Category>) :
    Action

// State
data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val categories: List<Category> = emptyList()
)

// View model
class CategoriesViewModel @Inject constructor(
    private val getCategoriesList: GetCategoriesList,
    private val homeNavigator: CategoriesNavigator,
    provider: CoroutineDispatcherProvider
) : UniViewModel<State>(provider.ioDispatcher()) {


    @ExperimentalCoroutinesApi
    override fun getDefaultState() = State(isLoading = true, showError = false)

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

    @ExperimentalCoroutinesApi
    override fun async(state: State, asyncAction: AsyncAction) {
        when (asyncAction) {
            is CategoryClicked -> homeNavigator.navCategoriesToCocktailsList(asyncAction.category)
            is LoadCategories -> {
                if (state.categories.isEmpty()) {
                    dispatch(LoadCategoriesStarted)
                    viewModelScope.launch {
                        getCategoriesList.invoke()
                            .catch { dispatch(LoadCategoriesFailed) }
                            .collect {
                                when (it.isEmpty()) {
                                    true -> dispatch(LoadCategoriesStarted)
                                    false -> dispatch(LoadCategoriesSuccess(it))
                                }
                            }
                    }
                }
            }
        }
    }
}