package com.renovavision.thecocktaildb.ui.dispatcher;

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    fun mainDispatcher(): CoroutineDispatcher
    fun ioDispatcher(): CoroutineDispatcher
}
