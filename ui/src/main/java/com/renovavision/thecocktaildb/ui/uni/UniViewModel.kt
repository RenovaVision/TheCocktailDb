package com.renovavision.thecocktaildb.ui.uni

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/***
 * Common interface of any app interaction
 */
interface Dispatchable

/***
 * Pure action, which allow modify State object using Reducer
 * Required get modified State after precessing.
 */
interface Action : Dispatchable

/***
 * Async action, which allow run any long-running operation.
 * Not required get modified State after precessing.
 */
interface AsyncAction : Dispatchable

/***
 * Pure function which deliver Dispatchables.
 */
typealias Dispatch = (dispatchable: Dispatchable) -> Unit

/***
 * Store class, based on AndroidX ViewModel.
 * Contains State, Reducer and Middleware
 */

abstract class UniViewModel<S>(dispatcher: CoroutineDispatcher) :
    ViewModel() {

    private var _state: S
    private val actionsChannel = Channel<Dispatchable>(Channel.UNLIMITED)
    private val stateChannel = Channel<S>(Channel.CONFLATED)

    private val scope = CoroutineScope(dispatcher)

    @ExperimentalCoroutinesApi
    open val dispatch: Dispatch = { actionsChannel.offer(it) }

    val state: LiveData<S> = stateChannel.receiveAsFlow().asLiveData()

    init {
        _state = getDefaultState()
        scope.launch { initStore() }
    }

    @ExperimentalCoroutinesApi
    private suspend fun initStore() {
        actionsChannel.consumeEach {
            when (it) {
                is Action -> {
                    _state = reduce(_state, it)
                    stateChannel.send(_state)
                }
                is AsyncAction -> {
                    async(_state, it)
                }
            }
        }
    }

    /***
     * Function, which return initial State of Store.
     */
    @ExperimentalCoroutinesApi
    abstract fun getDefaultState(): S

    /***
     * Pure function - Reducer, which provide new state depends on incoming Action
     */
    abstract fun reduce(state: S, action: Action): S

    /***
     * Async function - Middleware, which provide additional logic depends on incoming Async Action.
     * Can dispatch new Actions.
     */
    abstract fun async(state: S, asyncAction: AsyncAction)
}