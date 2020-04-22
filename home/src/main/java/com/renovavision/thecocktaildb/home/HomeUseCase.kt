package com.renovavision.thecocktaildb.home

import com.renovavision.thecocktaildb.network.DrinksByQuery.Drink
import com.renovavision.thecocktaildb.network.data.repo.SearchRepo
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val searchRepo: SearchRepo) {

    private val drinksList = mutableListOf<Drink>()

    suspend fun invoke(query: String): List<Drink> {
        val result = searchRepo.loadSearchResult(query)

        result.drinks.forEach {
            drinksList.add(Drink(it.strDrink, it.strDrinkThumb, it.key))
        }

        return drinksList
    }
}