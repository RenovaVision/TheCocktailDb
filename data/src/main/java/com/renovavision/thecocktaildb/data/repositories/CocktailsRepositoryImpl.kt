package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.api.CocktailsApi
import com.renovavision.thecocktaildb.data.entities.CocktailInfo.*
import com.renovavision.thecocktaildb.data.entities.DrinksByQuery.Drink
import com.renovavision.thecocktaildb.data.local.dao.CocktailsDao
import com.renovavision.thecocktaildb.data.mapper.cocktailInfoToEntityMapper
import com.renovavision.thecocktaildb.data.mapper.drinksToEntityMapper
import com.renovavision.thecocktaildb.domain.entities.CocktailInfoEntity.CocktailEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.domain.repositories.CocktailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class CocktailsRepositoryImpl @Inject constructor(
    private val cocktailsApi: CocktailsApi,
    private val cocktailsDao: CocktailsDao
) : CocktailsRepository {

    override suspend fun loadCocktailsListByIngredient(ingredient: String) =
        object : NetworkBoundRepository<List<DrinkEntity>, List<Drink>>() {

            override suspend fun saveRemoteData(response: List<Drink>) =
                cocktailsDao.insertDrinks(
                    response.map { it.copy(ingredient = ingredient) }
                )

            override fun fetchFromLocal() =
                cocktailsDao.getDrinks(ingredient).map {
                    it.map { drink ->
                        drinksToEntityMapper(drink)
                    }
                }

            override suspend fun fetchFromRemote() =
                cocktailsApi.loadDrinksByIngredient(ingredient).drinks
        }.asFlow().flowOn(Dispatchers.IO)

    override suspend fun loadCocktailsListByCategory(category: String) =
        object : NetworkBoundRepository<List<DrinkEntity>, List<Drink>>() {

            override suspend fun saveRemoteData(response: List<Drink>) =
                cocktailsDao.insertDrinks(
                    response.map { it.copy(category = category) }
                )

            override fun fetchFromLocal() =
                cocktailsDao.getDrinks(category).map {
                    it.map { drink ->
                        drinksToEntityMapper(drink)
                    }
                }

            override suspend fun fetchFromRemote() =
                cocktailsApi.loadDrinksByCategory(category).drinks
        }.asFlow().flowOn(Dispatchers.IO)

    override suspend fun loadCocktailDetails(id: Int) =
        object : NetworkBoundRepository<List<CocktailEntity>, List<Cocktail>>() {

            override suspend fun saveRemoteData(response: List<Cocktail>) =
                cocktailsDao.insertCocktailInfo(response)

            override fun fetchFromLocal() =
                cocktailsDao.getCocktailInfo(id).map {
                    it.map { info ->
                        cocktailInfoToEntityMapper(info)
                    }
                }

            override suspend fun fetchFromRemote() =
                cocktailsApi.loadCocktailInfoById(id).drinks
        }.asFlow().flowOn(Dispatchers.IO)
}