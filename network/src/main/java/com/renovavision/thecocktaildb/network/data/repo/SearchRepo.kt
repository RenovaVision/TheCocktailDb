package com.renovavision.thecocktaildb.network.data.repo

import com.renovavision.thecocktaildb.network.CocktailsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepo @Inject constructor(private val cocktailsApi: CocktailsApi) {

    suspend fun loadSearchResult(query: String) = cocktailsApi.searchCocktails(query)
}