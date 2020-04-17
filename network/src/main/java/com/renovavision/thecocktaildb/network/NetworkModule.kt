package com.renovavision.scorebat.network

import com.renovavision.thecocktaildb.network.CocktailsApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(
        @Named("cacheDir") httpCacheDir: File?,
        @Named("apiUrl") apiUrl: String
    ): Retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .callFactory(OkHttpClient.Builder().apply {
            httpCacheDir?.let { cache(Cache(it, (1024 * 1024 * 100))) }
        }.build())
        .addConverterFactory(
            MoshiConverterFactory.create()
        ).build()

    @Provides
    fun provideCocktailApi(retrofit: Retrofit) = retrofit.create(CocktailsApi::class.java)
}