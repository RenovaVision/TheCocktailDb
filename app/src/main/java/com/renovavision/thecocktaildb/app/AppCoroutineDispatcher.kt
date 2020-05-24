package com.renovavision.thecocktaildb.app

import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppCoroutineDispatcher @Inject constructor() :
    CoroutineDispatcherProvider {

    override fun mainDispatcher() = Dispatchers.Main

    override fun ioDispatcher() = Dispatchers.IO
}