package com.renovavision.thecocktaildb.app

import android.app.Application
import com.renovavision.thecocktaildb.BuildConfig
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppInjector
            .factory()
            .create(this, BuildConfig.API_URL, cacheDir)
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}