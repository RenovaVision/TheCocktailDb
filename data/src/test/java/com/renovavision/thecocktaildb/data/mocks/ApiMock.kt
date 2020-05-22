package com.renovavision.thecocktaildb.data.mocks

import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiMock {

    private val server = MockWebServer().apply {
        dispatcher = MockDispatcher
        start()
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl(server.url("/"))
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    fun <T> createService(service: Class<T>): T = retrofit.create(service)
}