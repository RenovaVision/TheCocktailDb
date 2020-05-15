package com.renovavision.thecocktaildb.data.repositories

import com.renovavision.thecocktaildb.data.api.CocktailsApi
import com.renovavision.thecocktaildb.data.mapper.cocktailInfoToEntityMapper
import com.renovavision.thecocktaildb.domain.repositories.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(private val cocktailsApi: CocktailsApi) :
    SearchRepository {

    override suspend fun loadSearchResult(query: String) =
        cocktailsApi.searchCocktails(query).drinks.map { cocktailInfoToEntityMapper(it) }
}