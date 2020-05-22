package com.renovavision.thecocktaildb.usecases

import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity
import com.renovavision.thecocktaildb.domain.repositories.CategoryRepository
import com.renovavision.thecocktaildb.domain.usecases.GetCategoriesList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertFails

@RunWith(MockitoJUnitRunner::class)
class GetCategoriesListTest {

    @Mock
    lateinit var categoriesRepo: CategoryRepository

    private lateinit var test: GetCategoriesList

    @Before
    fun init() {
        test = GetCategoriesList(categoriesRepo)
    }

    @Test
    fun `should return list of categories`() {
        val list = listOf(
            DrinksCategoryEntity.CategoryEntity(
                "One"
            ),
            DrinksCategoryEntity.CategoryEntity(
                "Two"
            )
        )
        runBlocking {
            `when`(categoriesRepo.loadCategories()).thenReturn(list)
            val result = test.invoke()
            assertEquals(
                DrinksCategoryEntity.CategoryEntity(
                    "One"
                ),
                result[0]
            )
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            `when`(categoriesRepo.loadCategories()).thenThrow(RuntimeException("Fake exception"))
            assertFails { test.invoke() }
        }
    }

}