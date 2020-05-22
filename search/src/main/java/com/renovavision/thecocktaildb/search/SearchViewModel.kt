package com.renovavision.thecocktaildb.search

import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.domain.usecases.GetSearchCocktails
import com.renovavision.thecocktaildb.ui.dispatcher.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.ui.utils.Action
import com.renovavision.thecocktaildb.ui.utils.AsyncAction
import com.renovavision.thecocktaildb.ui.utils.UniViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoadCocktails(val query: String) : AsyncAction
data class CocktailClicked(val cocktail: DrinkEntity) : AsyncAction

object LoadCocktailsStarted : Action
object LoadCocktailsFailed : Action
data class LoadCocktailsSuccess(val cocktails: List<DrinkEntity>) : Action

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val cocktails: List<DrinkEntity> = emptyList()
)

class SearchViewModel @Inject constructor(
    private val useCase: GetSearchCocktails,
    private val homeNavigator: SearchNavigator,
    provider: CoroutineDispatcherProvider
) : UniViewModel<State>(provider.ioDispatcher()) {

    override fun initState() = State(isLoading = true, showError = false)

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
            is CocktailClicked -> homeNavigator.navSearchToCocktailDetails(asyncAction.cocktail)
            is LoadCocktails -> loadCocktailsInfo(asyncAction.query)
        }
    }

    private fun loadCocktailsInfo(query: String) {
        if (query.length >= 3) {
            dispatch(LoadCocktailsStarted)

            viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
                dispatch(LoadCocktailsFailed)
            }) {
                val cocktails = useCase.invoke(query)
                when (cocktails.isEmpty()) {
                    true -> dispatch(LoadCocktailsFailed)
                    else -> dispatch(LoadCocktailsSuccess(cocktails))
                }
            }
        } else {
            dispatch(LoadCocktailsFailed)
        }
    }
}