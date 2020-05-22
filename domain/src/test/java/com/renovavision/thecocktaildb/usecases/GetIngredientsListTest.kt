package com.renovavision.thecocktaildb.usecases

import com.nhaarman.mockitokotlin2.mock
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity
import com.renovavision.thecocktaildb.domain.repositories.IngredientsRepository
import com.renovavision.thecocktaildb.domain.usecases.GetIngredientsList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals
import kotlin.test.assertFails

class GetIngredientsListTest {

    private var ingredientsRepository: IngredientsRepository = mock()

    private lateinit var test: GetIngredientsList

    @Before
    fun init() {
        test = GetIngredientsList(ingredientsRepository)
    }

    @Test
    fun `should return list of ingredients`() {
        val list = listOf(
            DrinksIngredientEntity.IngredientEntity(
                "One"
            ),
            DrinksIngredientEntity.IngredientEntity(
                "Two"
            )
        )
        runBlocking {
            `when`(ingredientsRepository.loadIngredients()).thenReturn(flow {
                emit(list)
            })
            test.invoke().collect {
                assertEquals(
                    DrinksIngredientEntity.IngredientEntity(
                        "One"
                    ),
                    it[0]
                )
            }
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            `when`(ingredientsRepository.loadIngredients()).thenThrow(RuntimeException("Fake exception"))
            assertFails { test.invoke() }
        }
    }

}