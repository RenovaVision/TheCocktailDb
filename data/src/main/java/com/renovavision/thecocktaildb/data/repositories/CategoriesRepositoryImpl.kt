package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.network.Api
import com.renovavision.thecocktaildb.data.database.dao.CategoriesDao
import com.renovavision.thecocktaildb.data.mapper.categoryMapper
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.repositories.CategoriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepositoryImpl @Inject constructor(
    private val api: Api,
    private val categoriesDao: CategoriesDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : CategoriesRepository {

    @ExperimentalCoroutinesApi
    override suspend fun loadCategories() = networkBoundedFlow(
        categoriesDao.getAllCategories().map { it.map { categoryMapper(it) } },
        { categoriesDao.insertCategories(it) },
        { api.loadCategories().drinks }
    ).flowOn(coroutineDispatcherProvider.ioDispatcher())
}