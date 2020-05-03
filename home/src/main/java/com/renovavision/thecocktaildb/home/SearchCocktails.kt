package com.renovavision.thecocktaildb.home

import com.renovavision.thecocktaildb.network.DrinksByQuery.Drink
import com.renovavision.thecocktaildb.network.data.repo.SearchRepo
import javax.inject.Inject

class SearchCocktails @Inject constructor(private val searchRepo: SearchRepo) {

    suspend fun invoke(query: String): List<Drink> {
        val result = searchRepo.loadSearchResult(query)

        return result.drinks.map { Drink(it.strDrink, it.strDrinkThumb, it.key) }
    }
}