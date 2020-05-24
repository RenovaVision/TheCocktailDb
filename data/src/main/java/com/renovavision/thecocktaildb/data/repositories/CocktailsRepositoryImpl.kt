package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.network.Api
import com.renovavision.thecocktaildb.data.database.dao.CocktailsDao
import com.renovavision.thecocktaildb.data.mapper.cocktailDetailsMapper
import com.renovavision.thecocktaildb.data.mapper.cocktailMapper
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailsRepositoryImpl @Inject constructor(
    private val api: Api,
    private val cocktailsDao: CocktailsDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : CocktailsRepository {

    @ExperimentalCoroutinesApi
    override suspend fun loadCocktailsListByIngredient(ingredient: String) = networkBoundedFlow(
        cocktailsDao.getCocktails(ingredient).map { it.map { cocktailMapper(it) } },
        { cocktailsDao.insertCocktails(it.map { it.copy(ingredient = ingredient) }) },
        { api.loadCocktailsByIngredient(ingredient).drinks }
    ).flowOn(coroutineDispatcherProvider.ioDispatcher())

    @ExperimentalCoroutinesApi
    override suspend fun loadCocktailsListByCategory(category: String) = networkBoundedFlow(
        cocktailsDao.getCocktails(category).map { it.map { cocktailMapper(it) } },
        { cocktailsDao.insertCocktails(it.map { it.copy(category = category) }) },
        { api.loadCocktailsByCategory(category).drinks }
    ).flowOn(coroutineDispatcherProvider.ioDispatcher())

    @ExperimentalCoroutinesApi
    override suspend fun loadCocktailDetails(id: Int) = networkBoundedFlow(
        cocktailsDao.getCocktailDetails(id).map { it?.let(cocktailDetailsMapper) },
        { cocktailsDao.insertCocktailDetails(listOf(it)) },
        { api.loadCocktailInfoById(id).drinks.first() }
    ).flowOn(coroutineDispatcherProvider.ioDispatcher())
}