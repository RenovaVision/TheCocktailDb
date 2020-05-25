package com.renovavision.thecocktaildb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.entities.Ingredient
import com.renovavision.thecocktaildb.domain.repositories.IngredientsRepository
import com.renovavision.thecocktaildb.domain.usecases.GetIngredientsList
import com.renovavision.thecocktaildb.ingredients.IngredientsNavigator
import com.renovavision.thecocktaildb.ingredients.IngredientsViewModel
import com.renovavision.thecocktaildb.ingredients.LoadIngredients
import com.renovavision.thecocktaildb.ingredients.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import uk.co.jemos.podam.api.PodamFactoryImpl
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class IngredientsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val ingredientsRepo: IngredientsRepository = mock()

    private val navigator: IngredientsNavigator = mock()

    private val dispatcherProvider: CoroutineDispatcherProvider = mock()

    private val observer: Observer<State> = mock()

    private lateinit var viewModel: IngredientsViewModel

    @Before
    fun beforeTest() {
        Dispatchers.setMain(testDispatcher)
        `when`(dispatcherProvider.ioDispatcher()).thenReturn(testDispatcher)
        viewModel =
            IngredientsViewModel(GetIngredientsList(ingredientsRepo), navigator, dispatcherProvider)
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `success scenario`() {
        runBlockingTest {
            val entity = listOf(entityFactory())
            `when`(ingredientsRepo.loadIngredients()).thenReturn(flow { emit(entity) })
            viewModel.dispatch(LoadIngredients)
            verify(observer).onChanged(
                State(
                    isLoading = true,
                    showError = false,
                    ingredients = emptyList()
                )
            )

            val captor = ArgumentCaptor.forClass(State::class.java)
            captor.run {
                verify(observer, times(2)).onChanged(capture())
                assertEquals(entity, captor.value.ingredients)
            }
            verifyNoMoreInteractions(observer)
        }
    }

    @Test
    fun `failed scenario`() {
        runBlockingTest {
            `when`(ingredientsRepo.loadIngredients()).thenThrow(RuntimeException("Mock error"))
            viewModel.dispatch(LoadIngredients)
            verify(observer).onChanged(
                State(
                    isLoading = true,
                    showError = false,
                    ingredients = emptyList()
                )
            )
            verify(observer).onChanged(
                State(
                    isLoading = false,
                    showError = true,
                    ingredients = emptyList()
                )
            )
            verifyNoMoreInteractions(observer)
        }
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
    }

    private fun entityFactory() =
        PodamFactoryImpl().manufacturePojo(Ingredient::class.java)
}