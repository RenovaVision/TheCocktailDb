package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.api.CocktailsApi
import com.renovavision.thecocktaildb.data.entities.DrinksCategory.Category
import com.renovavision.thecocktaildb.data.local.dao.CategoriesDao
import com.renovavision.thecocktaildb.data.mapper.drinksCategoryToEntityMapper
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity.CategoryEntity
import com.renovavision.thecocktaildb.domain.repositories.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val cocktailsApi: CocktailsApi,
    private val categoriesDao: CategoriesDao
) : CategoryRepository {

    override suspend fun loadCategories() =
        object : NetworkBoundRepository<List<CategoryEntity>, List<Category>>() {

            override suspend fun saveRemoteData(response: List<Category>) =
                categoriesDao.insertCategories(response)

            override fun fetchFromLocal() =
                categoriesDao.getAllCategories().map {
                    it.map { category ->
                        drinksCategoryToEntityMapper(category)
                    }
                }

            override suspend fun fetchFromRemote() =
                cocktailsApi.loadDrinksCategory().drinks

        }.asFlow().flowOn(Dispatchers.IO)
}