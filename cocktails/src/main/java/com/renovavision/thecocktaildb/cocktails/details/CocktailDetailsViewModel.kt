package com.renovavision.thecocktaildb.cocktails.details

import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.entities.Cocktail
import com.renovavision.thecocktaildb.domain.entities.CocktailDetails
import com.renovavision.thecocktaildb.domain.usecases.GetCocktailDetails
import com.renovavision.thecocktaildb.ui.uni.Action
import com.renovavision.thecocktaildb.ui.uni.AsyncAction
import com.renovavision.thecocktaildb.ui.uni.UniViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoadCocktailDetails(val drink: Cocktail) : AsyncAction

object LoadCocktailInfoStarted : Action
object LoadCocktailInfoFailed : Action
data class LoadCocktailInfoSuccess(val cocktailDetails: CocktailDetails) :
    Action

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val cocktailDetails: CocktailDetails? = null
)

@ExperimentalCoroutinesApi
class CocktailDetailsViewModel @Inject constructor(
    private val getCocktailDetails: GetCocktailDetails,
    provider: CoroutineDispatcherProvider
) : UniViewModel<State>(provider.ioDispatcher()) {

    override fun getDefaultState() = State(isLoading = true, showError = false)

    override fun reduce(state: State, action: Action): State =
        when (action) {
            is LoadCocktailInfoStarted -> state.copy(isLoading = true)
            is LoadCocktailInfoFailed -> state.copy(isLoading = false, showError = true)
            is LoadCocktailInfoSuccess -> state.copy(
                isLoading = false,
                showError = false,
                cocktailDetails = action.cocktailDetails
            )
            else -> state
        }

    @InternalCoroutinesApi
    override fun async(state: State, asyncAction: AsyncAction) {
        when (asyncAction) {
            is LoadCocktailDetails -> if (state.cocktailDetails == null) {
                dispatch(LoadCocktailInfoStarted)
                viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
                    dispatch(LoadCocktailInfoFailed)
                }) {
                    getCocktailDetails.invoke(asyncAction.drink.key)
                        .catch { LoadCocktailInfoFailed }
                        .collect { dispatch(LoadCocktailInfoSuccess(it)) }
                }
            }
        }
    }
}