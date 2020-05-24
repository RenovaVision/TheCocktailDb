package com.renovavision.thecocktaildb.data.network

import com.renovavision.thecocktaildb.data.mocks.ApiMock
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertTrue

class ApiTest {

    private val api = ApiMock.createService(Api::class.java)

    @Test
    fun `should return list of categories`() {
        runBlocking {
            val data = api.loadCategories()
            assertTrue { data.drinks.isNotEmpty() }
        }
    }

    // TODO: add more tests
}