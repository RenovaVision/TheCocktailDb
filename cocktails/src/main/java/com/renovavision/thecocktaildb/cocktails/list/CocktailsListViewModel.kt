package com.renovavision.thecocktaildb.cocktails.list

import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.cocktails.CocktailsNavigator
import com.renovavision.thecocktaildb.domain.usecases.GetCocktailsListByCategory
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.entities.Category
import com.renovavision.thecocktaildb.domain.entities.Cocktail
import com.renovavision.thecocktaildb.domain.entities.Ingredient
import com.renovavision.thecocktaildb.domain.usecases.GetCocktailsListByIngredient
import com.renovavision.thecocktaildb.ui.uni.Action
import com.renovavision.thecocktaildb.ui.uni.AsyncAction
import com.renovavision.thecocktaildb.ui.uni.UniViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

data class LoadCocktailsByIngredient(val ingredient: Ingredient) :
    AsyncAction

data class LoadCocktailsByCategory(val category: Category) :
    AsyncAction

class CocktailClicked(
    val cocktail: Cocktail,
    val imageView: WeakReference<AppCompatImageView>
) : AsyncAction

object LoadCocktailsStarted : Action
object LoadCocktailsFailed : Action
data class LoadCocktailsSuccess(val cocktails: List<Cocktail>) :
    Action

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val cocktails: List<Cocktail> = emptyList()
)

@ExperimentalCoroutinesApi
class CocktailsListViewModel @Inject constructor(
    private val getCocktailsListByCategory: GetCocktailsListByCategory,
    private val getCocktailsListByIngredient: GetCocktailsListByIngredient,
    private val cocktailsNavigator: CocktailsNavigator,
    provider: CoroutineDispatcherProvider
) : UniViewModel<State>(provider.ioDispatcher()) {

    override fun getDefaultState() = State(isLoading = true, showError = false)

    override fun reduce(state: State, action: Action): State =
        when (action) {
            is LoadCocktailsStarted -> state.copy(isLoading = true)
            is LoadCocktailsFailed -> state.copy(isLoading = false, showError = true)
            is LoadCocktailsSuccess -> state.copy(
                isLoading = false,
                showError = false,
                cocktails = action.cocktails
            )
            else -> state
        }

    override fun async(state: State, asyncAction: AsyncAction) {
        when (asyncAction) {
            is CocktailClicked -> cocktailsNavigator.navCocktailsListToDetails(
                asyncAction.cocktail,
                asyncAction.imageView
            )
            is LoadCocktailsByIngredient -> loadCocktailsListByIngredient(
                state,
                asyncAction.ingredient
            )
            is LoadCocktailsByCategory -> loadCocktailsListByCategory(state, asyncAction.category)
        }
    }

    private fun loadCocktailsListByIngredient(state: State, ingredient: Ingredient) {
        if (state.cocktails.isEmpty()) {
            dispatch(LoadCocktailsStarted)
            viewModelScope.launch {
                getCocktailsListByIngredient.invoke(ingredient.key)
                    .catch { dispatch(LoadCocktailsFailed) }
                    .collect { dispatch(LoadCocktailsSuccess(it)) }
            }
        }
    }

    private fun loadCocktailsListByCategory(state: State, category: Category) {
        if (state.cocktails.isEmpty()) {
            dispatch(LoadCocktailsStarted)
            viewModelScope.launch {
                getCocktailsListByCategory.invoke(category.key)
                    .catch { dispatch(LoadCocktailsFailed) }
                    .collect { dispatch(LoadCocktailsSuccess(it)) }
            }
        }
    }
}