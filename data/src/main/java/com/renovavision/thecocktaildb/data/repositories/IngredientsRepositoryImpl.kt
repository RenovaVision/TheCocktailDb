package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.network.Api
import com.renovavision.thecocktaildb.data.database.dao.IngredientsDao
import com.renovavision.thecocktaildb.data.mapper.ingredientMapper
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.repositories.IngredientsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientsRepositoryImpl @Inject constructor(
    private val api: Api,
    private val ingredientsDao: IngredientsDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : IngredientsRepository {

    @ExperimentalCoroutinesApi
    override suspend fun loadIngredients() = networkBoundedFlow(
        ingredientsDao.getAllIngredients().map { it.map { ingredientMapper(it) } },
        { ingredientsDao.insertIngredients(it) },
        { api.loadIngredients().drinks }
    ).flowOn(coroutineDispatcherProvider.ioDispatcher())
}