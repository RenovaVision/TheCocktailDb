package com.renovavision.thecocktaildb.usecases

import com.renovavision.thecocktaildb.domain.entities.CocktailDetails
import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import com.renovavision.thecocktaildb.domain.usecases.GetCocktailDetails
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import uk.co.jemos.podam.api.PodamFactoryImpl
import kotlin.test.assertEquals
import kotlin.test.assertFails

@RunWith(MockitoJUnitRunner::class)
class GetCocktailDetailsTest {

    @Mock
    lateinit var cocktailsRepository: CocktailsRepository

    private lateinit var test: GetCocktailDetails

    @Before
    fun init() {
        test = GetCocktailDetails(cocktailsRepository)
    }

    @Test
    fun `should return cocktail details`() {
        runBlocking {
            val cocktailDetails = entityFactory(11007, "Margarita")

            `when`(cocktailsRepository.loadCocktailDetails(anyInt())).thenReturn(
                flow {
                    emit(cocktailDetails)
                })

            test.invoke(anyInt()).collect {
                assertEquals(11007, it.key)
                assertEquals("Margarita", it.strDrink)
            }
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            `when`(cocktailsRepository.loadCocktailDetails(anyInt())).thenThrow(
                RuntimeException("Fake exception")
            )
            assertFails { test.invoke(anyInt()) }
        }
    }

    private fun entityFactory(key: Int, drink: String) =
        PodamFactoryImpl().manufacturePojo(CocktailDetails::class.java)
            .copy(key = key, strDrink = drink)
}