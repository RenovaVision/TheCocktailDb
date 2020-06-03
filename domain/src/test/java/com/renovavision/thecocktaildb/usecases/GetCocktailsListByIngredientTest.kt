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
import uk.co.jemos.podam.api.PodamFactoryImpl
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
            val cocktails = listOf(entityFactory(15346))

            Mockito.`when`(cocktailsRepository.loadCocktailsListByIngredient(Mockito.anyString()))
                .thenReturn(
                    flow {
                        emit(cocktails)
                    })
            test.invoke(Mockito.anyString()).collect {
                assertEquals(
                    15346, it[0].key
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

    private fun entityFactory(key: Int) =
        PodamFactoryImpl().manufacturePojo(Cocktail::class.java)
            .copy(key = key)
}