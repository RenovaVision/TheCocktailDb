package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.api.CocktailsApi
import com.renovavision.thecocktaildb.data.mapper.Mapper
import com.renovavision.thecocktaildb.domain.repositories.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(private val cocktailsApi: CocktailsApi) :
    SearchRepository {

    private val mapper = Mapper()

    override suspend fun loadSearchResult(query: String) =
        cocktailsApi.searchCocktails(query).drinks.map { mapper.mapCocktailToEntity(it) }
}