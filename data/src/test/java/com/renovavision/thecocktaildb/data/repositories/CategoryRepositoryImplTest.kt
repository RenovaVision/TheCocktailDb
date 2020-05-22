package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.api.CocktailsApi
import com.renovavision.thecocktaildb.data.mocks.ApiMock
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertTrue

class CategoryRepositoryImplTest {

    private val repository = CategoryRepositoryImpl(ApiMock.createService(CocktailsApi::class.java))

    @Test
    fun `should return list of categories`() {
        runBlocking {
            val categories = repository.loadCategories()
            assertTrue { categories.isNotEmpty() }
        }
    }
}