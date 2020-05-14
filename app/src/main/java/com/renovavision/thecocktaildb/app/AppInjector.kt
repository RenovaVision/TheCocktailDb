package com.renovavision.thecocktaildb.app

import android.app.Application
import com.renovavision.thecocktaildb.activity.MainActivityModule
import com.renovavision.thecocktaildb.data.NetworkModule
import com.renovavision.thecocktaildb.data.RepositoryModule
import com.renovavision.thecocktaildb.navigation.NavigationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        MainActivityModule::class,
        RepositoryModule::class,
        NetworkModule::class,
        NavigationModule::class
    ]
)
@Singleton
interface AppInjector {

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
            @BindsInstance @Named("apiUrl")
            apiUrl: String,
            @BindsInstance @Named("cacheDir")
            cacheDir: File?
        ): AppInjector
    }

    fun inject(app: App)
}