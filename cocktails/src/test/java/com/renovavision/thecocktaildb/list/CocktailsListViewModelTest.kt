package com.renovavision.thecocktaildb.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.renovavision.thecocktaildb.cocktails.CocktailsNavigator
import com.renovavision.thecocktaildb.cocktails.list.CocktailsListViewModel
import com.renovavision.thecocktaildb.cocktails.list.LoadCocktailsByCategory
import com.renovavision.thecocktaildb.cocktails.list.LoadCocktailsByIngredient
import com.renovavision.thecocktaildb.cocktails.list.State
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.entities.Category
import com.renovavision.thecocktaildb.domain.entities.Cocktail
import com.renovavision.thecocktaildb.domain.entities.Ingredient
import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import com.renovavision.thecocktaildb.domain.usecases.GetCocktailsListByCategory
import com.renovavision.thecocktaildb.domain.usecases.GetCocktailsListByIngredient
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
class CocktailsListViewModelTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatchers = TestCoroutineDispatcher()

    private val cocktailsRepo: CocktailsRepository = mock()

    private val dispatcherProvider: CoroutineDispatcherProvider = mock()

    private val observer: Observer<State> = mock()

    private val navigator: CocktailsNavigator = mock()

    private lateinit var viewModel: CocktailsListViewModel

    @Before
    fun beforeTest() {
        Dispatchers.setMain(testDispatchers)
        `when`(dispatcherProvider.ioDispatcher()).thenReturn(testDispatchers)
        viewModel = CocktailsListViewModel(
            GetCocktailsListByCategory(cocktailsRepo),
            GetCocktailsListByIngredient(cocktailsRepo),
            navigator,
            dispatcherProvider
        )
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `success load cocktails list by ingredient`() {
        runBlockingTest {
            val entity = listOf(entityFactory(1))
            `when`(cocktailsRepo.loadCocktailsListByIngredient("scotch")).thenReturn(flow {
                emit(
                    entity
                )
            })
            viewModel.dispatch(LoadCocktailsByIngredient(Ingredient("scotch")))
            verify(observer).onChanged(
                State(
                    isLoading = true,
                    showError = false,
                    cocktails = emptyList()
                )
            )

            val captor = ArgumentCaptor.forClass(State::class.java)
            captor.run {
                verify(observer, times(2)).onChanged(capture())
                assertEquals(entity, captor.value.cocktails)
            }
            verifyNoMoreInteractions(observer)
        }
    }

    @Test
    fun `failed load cocktails list by ingredient`() {
        runBlockingTest {
            `when`(cocktailsRepo.loadCocktailsListByIngredient("scotch")).thenThrow(
                RuntimeException("Mock error")
            )
            viewModel.dispatch(LoadCocktailsByIngredient(Ingredient("scotch")))
            verify(observer).onChanged(
                State(
                    isLoading = true,
                    showError = false,
                    cocktails = emptyList()
                )
            )
            verify(observer).onChanged(
                State(
                    isLoading = false,
                    showError = true,
                    cocktails = emptyList()
                )
            )
            verifyNoMoreInteractions(observer)
        }
    }

    @Test
    fun `success load cocktails list by category`() {
        runBlockingTest {
            val entity = listOf(entityFactory(1))
            `when`(cocktailsRepo.loadCocktailsListByCategory("cocktail")).thenReturn(flow {
                emit(
                    entity
                )
            })
            viewModel.dispatch(LoadCocktailsByCategory(Category("cocktail")))
            verify(observer).onChanged(
                State(
                    isLoading = true,
                    showError = false,
                    cocktails = emptyList()
                )
            )

            val captor = ArgumentCaptor.forClass(State::class.java)
            captor.run {
                verify(observer, times(2)).onChanged(capture())
                assertEquals(entity, captor.value.cocktails)
            }
            verifyNoMoreInteractions(observer)
        }
    }

    @Test
    fun `failed load cocktails list by category`() {
        runBlockingTest {
            `when`(cocktailsRepo.loadCocktailsListByCategory("cocktail")).thenThrow(
                RuntimeException("Mock error")
            )
            viewModel.dispatch(LoadCocktailsByCategory(Category("cocktail")))
            verify(observer).onChanged(
                State(
                    isLoading = true,
                    showError = false,
                    cocktails = emptyList()
                )
            )
            verify(observer).onChanged(
                State(
                    isLoading = false,
                    showError = true,
                    cocktails = emptyList()
                )
            )
            verifyNoMoreInteractions(observer)
        }
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
    }

    private fun entityFactory(key: Int) =
        PodamFactoryImpl().manufacturePojo(Cocktail::class.java).copy(key = key)
}