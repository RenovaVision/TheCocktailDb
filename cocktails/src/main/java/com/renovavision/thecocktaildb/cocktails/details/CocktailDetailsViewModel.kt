package com.renovavision.thecocktaildb.cocktails.details

import androidx.lifecycle.viewModelScope
import com.renovavision.thecocktaildb.domain.entities.CocktailInfoEntity.CocktailEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.domain.usecases.GetCocktails
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.ui.utils.Action
import com.renovavision.thecocktaildb.ui.utils.AsyncAction
import com.renovavision.thecocktaildb.ui.utils.UniViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoadCocktailInfo(val drink: DrinkEntity) : AsyncAction

object LoadCocktailInfoStarted : Action
object LoadCocktailInfoFailed : Action
data class LoadCocktailInfoSuccess(val cocktailInfo: CocktailEntity) : Action

data class State(
    val isLoading: Boolean,
    val showError: Boolean,
    val cocktailInfo: CocktailEntity? = null
)

@ExperimentalCoroutinesApi
class CocktailDetailsViewModel @Inject constructor(
    private val getCocktails: GetCocktails,
    provider: CoroutineDispatcherProvider
) : UniViewModel<State>(provider.ioDispatcher()) {

    override fun initState() = State(isLoading = true, showError = false)

    override fun reduce(state: State, action: Action): State =
        when (action) {
            is LoadCocktailInfoStarted -> state.copy(isLoading = true)
            is LoadCocktailInfoFailed -> state.copy(isLoading = false, showError = true)
            is LoadCocktailInfoSuccess -> state.copy(
                isLoading = false,
                showError = false,
                cocktailInfo = action.cocktailInfo
            )
            else -> state
        }

    override fun async(state: State, asyncAction: AsyncAction) {
        when (asyncAction) {
            is LoadCocktailInfo -> loadCocktailInfo(state, asyncAction.drink)
        }
    }

    private fun loadCocktailInfo(state: State, cocktail: DrinkEntity) {
        if (state.cocktailInfo == null) {
            dispatch(LoadCocktailInfoStarted)
            viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
                dispatch(LoadCocktailInfoFailed)
            }) {
                val cocktailInfo = getCocktails.loadCocktailDetails(cocktail.key)
                cocktailInfo.collect {
                    when (it.isEmpty()) {
                        true -> dispatch(LoadCocktailInfoFailed)
                        else -> dispatch(LoadCocktailInfoSuccess(it.first()))
                    }
                }
            }
        }
    }
}