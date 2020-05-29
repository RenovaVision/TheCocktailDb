package com.renovavision.thecocktaildb.data.network

import com.renovavision.thecocktaildb.data.mocks.ApiMock
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
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

    @Test
    fun `should return list of ingredients`() {
        runBlocking {
            val data = api.loadIngredients()
            assertTrue { data.drinks.isNotEmpty() }
        }
    }

    @Test
    fun `should return list of cocktails by category`() {
        runBlocking {
            val data = api.loadCocktailsByCategory(anyString())
            assertTrue { data.drinks.isNotEmpty() }
        }
    }

    @Test
    fun `should return list of cocktails by ingredient`() {
        runBlocking {
            val data = api.loadCocktailsByIngredient(anyString())
            assertTrue { data.drinks.isNotEmpty() }
        }
    }

    @Test
    fun `should return cocktail details`() {
        runBlocking {
            val data = api.loadCocktailInfoById(anyInt())
            assertTrue { data.drinks.isNotEmpty() }
        }
    }
}