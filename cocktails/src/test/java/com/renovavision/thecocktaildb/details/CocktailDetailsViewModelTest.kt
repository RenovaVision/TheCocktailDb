package com.renovavision.thecocktaildb.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.renovavision.thecocktaildb.cocktails.details.CocktailDetailsViewModel
import com.renovavision.thecocktaildb.cocktails.details.LoadCocktailDetails
import com.renovavision.thecocktaildb.cocktails.details.State
import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.entities.Cocktail
import com.renovavision.thecocktaildb.domain.entities.CocktailDetails
import com.renovavision.thecocktaildb.domain.usecases.GetCocktailDetails
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
class CocktailDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val cocktailsRepo: CocktailsRepository = mock()

    private val dispatcherProvider: CoroutineDispatcherProvider = mock()

    private val observer: Observer<State> = mock()

    private lateinit var viewModel: CocktailDetailsViewModel

    @Before
    fun beforeTest() {
        Dispatchers.setMain(testDispatcher)
        `when`(dispatcherProvider.ioDispatcher()).thenReturn(testDispatcher)
        viewModel = CocktailDetailsViewModel(GetCocktailDetails(cocktailsRepo), dispatcherProvider)
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `success scenario`() {
        runBlockingTest {
            val entity = entityFactory(1)
            `when`(cocktailsRepo.loadCocktailDetails(1)).thenReturn(flow { emit(entity) })
            viewModel.dispatch(LoadCocktailDetails(Cocktail(1,"name", "url")))
            verify(observer).onChanged(State(true, false, null))

            val captor = ArgumentCaptor.forClass(State::class.java)
            captor.run {
                verify(observer, times(2)).onChanged(capture())
                assertEquals(entity, captor.value.cocktailDetails!!)
            }
            verifyNoMoreInteractions(observer)
        }
    }

    @Test
    fun `failed scenario`() {
        runBlockingTest {
            `when`(cocktailsRepo.loadCocktailDetails(1)).thenThrow(RuntimeException("Mock error"))
            viewModel.dispatch(LoadCocktailDetails(Cocktail(1, "name", "url")))
            verify(observer).onChanged(State(true, false, null))
            verify(observer).onChanged(State(false, true, null))
            verifyNoMoreInteractions(observer)
        }
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
    }

    private fun entityFactory(key: Int) =
        PodamFactoryImpl().manufacturePojo(CocktailDetails::class.java).copy(key = key)
}