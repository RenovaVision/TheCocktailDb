package com.renovavision.thecocktaildb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.renovavision.thecocktaildb.categories.*
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.entities.Category
import com.renovavision.thecocktaildb.domain.repositories.CategoriesRepository
import com.renovavision.thecocktaildb.domain.usecases.GetCategoriesList
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
class CategoriesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val categoriesRepo: CategoriesRepository = mock()

    private val navigator: CategoriesNavigator = mock()

    private val dispatcherProvider: CoroutineDispatcherProvider = mock()

    private val observer: Observer<State> = mock()

    private lateinit var viewModel: CategoriesViewModel

    @Before
    fun beforeTest() {
        Dispatchers.setMain(testDispatcher)
        `when`(dispatcherProvider.ioDispatcher()).thenReturn(testDispatcher)
        viewModel =
            CategoriesViewModel(GetCategoriesList(categoriesRepo), navigator, dispatcherProvider)
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `success scenario`() {
        runBlockingTest {
            val entity = listOf(entityFactory())
            `when`(categoriesRepo.loadCategories()).thenReturn(flow { emit(entity) })
            viewModel.dispatch(LoadCategories)
            verify(observer).onChanged(
                State(
                    isLoading = true,
                    showError = false,
                    categories = emptyList()
                )
            )

            val captor = ArgumentCaptor.forClass(State::class.java)
            captor.run {
                verify(observer, times(2)).onChanged(capture())
                assertEquals(entity, captor.value.categories)
            }
            verifyNoMoreInteractions(observer)
        }
    }

    @Test
    fun `failed scenario`() {
        runBlockingTest {
            `when`(categoriesRepo.loadCategories()).thenThrow(RuntimeException("Mock error"))
            viewModel.dispatch(LoadCategories)
            verify(observer).onChanged(
                State(
                    isLoading = true,
                    showError = false,
                    categories = emptyList()
                )
            )
            verify(observer).onChanged(
                State(
                    isLoading = false,
                    showError = true,
                    categories = emptyList()
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
        PodamFactoryImpl().manufacturePojo(Category::class.java)
}