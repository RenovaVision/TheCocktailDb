package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.api.CocktailsApi
import com.renovavision.thecocktaildb.data.entities.DrinksIngredient.*
import com.renovavision.thecocktaildb.data.database.dao.IngredientsDao
import com.renovavision.thecocktaildb.data.mapper.drinksIngredientToEntityMapper
import com.renovavision.thecocktaildb.domain.CoroutineDispatcherProvider
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.*
import com.renovavision.thecocktaildb.domain.repositories.IngredientsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class IngredientsRepositoryImpl @Inject constructor(
    private val cocktailsApi: CocktailsApi,
    private val ingredientsDao: IngredientsDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : IngredientsRepository {

    override suspend fun loadIngredients() =
        object : NetworkBoundRepository<List<IngredientEntity>, List<Ingredient>>() {

            override suspend fun saveRemoteData(response: List<Ingredient>) {
                ingredientsDao.insertIngredients(response)
            }

            override fun fetchFromLocal() =
                ingredientsDao.getAllIngredients().map {
                    it.map { ingredient ->
                        drinksIngredientToEntityMapper(ingredient)
                    }
                }

            override suspend fun fetchFromRemote() =
                cocktailsApi.loadDrinksIngredient().drinks
        }.asFlow().flowOn(coroutineDispatcherProvider.ioDispatcher())
}