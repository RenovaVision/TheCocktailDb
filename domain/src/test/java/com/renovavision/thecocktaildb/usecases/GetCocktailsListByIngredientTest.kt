package com.renovavision.thecocktaildb.usecases

import com.renovavision.thecocktaildb.domain.entities.Cocktail
import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import com.renovavision.thecocktaildb.domain.usecases.GetCocktailsListByIngredient
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertFails

@RunWith(MockitoJUnitRunner::class)
class GetCocktailsListByIngredientTest {

    @Mock
    lateinit var cocktailsRepository: CocktailsRepository

    private lateinit var test: GetCocktailsListByIngredient

    @Before
    fun init() {
        test = GetCocktailsListByIngredient(cocktailsRepository)
    }

    @Test
    fun `should return list of cocktails by ingredient`() {
        runBlocking {
            Mockito.`when`(cocktailsRepository.loadCocktailsListByIngredient(Mockito.anyString()))
                .thenReturn(
                    flow {
                        emit(cocktailsList)
                    })
            test.invoke(Mockito.anyString()).collect {
                assertEquals(
                    "155 Belmont", it[1].strDrink
                )
            }
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            Mockito.`when`(cocktailsRepository.loadCocktailsListByIngredient(Mockito.anyString()))
                .thenThrow(
                    RuntimeException("Fake exception")
                )
            assertFails { test.invoke(Mockito.anyString()) }
        }
    }
}