package com.renovavision.thecocktaildb.app

import com.renovavision.thecocktaildb.ui.dispatcher.CoroutineDispatcherProvider
import dagger.Binds
import dagger.Module

@Module
interface AppModule {

    @Binds
    fun provideCoroutineDispatcher(provider: AppCoroutineDispatcher): CoroutineDispatcherProvider

}