package com.renovavision.thecocktaildb.data.api

import com.renovavision.thecocktaildb.data.mocks.ApiMock
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertTrue

class CocktailsApiTest {

    private val api = ApiMock.createService(CocktailsApi::class.java)

    @Test
    fun `should return list of categories`() {
        runBlocking {
            val data = api.loadDrinksCategory()
            assertTrue { data.drinks.isNotEmpty() }
        }
    }

    // TODO: add more tests
}