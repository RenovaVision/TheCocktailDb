package com.renovavision.thecocktaildb.usecases

import com.renovavision.thecocktaildb.domain.entities.Cocktail
import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import com.renovavision.thecocktaildb.domain.usecases.GetCocktailsListByCategory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.junit.MockitoJUnitRunner
import uk.co.jemos.podam.api.PodamFactoryImpl
import kotlin.test.assertEquals
import kotlin.test.assertFails

@RunWith(MockitoJUnitRunner::class)
class GetCocktailsListByCategoryTest {

    @Mock
    lateinit var cocktailsRepository: CocktailsRepository

    private lateinit var test: GetCocktailsListByCategory

    @Before
    fun init() {
        test = GetCocktailsListByCategory(cocktailsRepository)
    }

    @Test
    fun `should return list of cocktails by category`() {
        runBlocking {
            val cocktails = listOf(entityFactory(178318))

            `when`(cocktailsRepository.loadCocktailsListByCategory(anyString())).thenReturn(
                flow {
                    emit(cocktails)
                })
            test.invoke(anyString()).collect {
                assertEquals(
                    178318, it[0].key
                )
            }
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            `when`(cocktailsRepository.loadCocktailsListByCategory(anyString())).thenThrow(
                RuntimeException("Fake exception")
            )
            assertFails { test.invoke(anyString()) }
        }
    }

    private fun entityFactory(key: Int) =
        PodamFactoryImpl().manufacturePojo(Cocktail::class.java)
            .copy(key = key)
}