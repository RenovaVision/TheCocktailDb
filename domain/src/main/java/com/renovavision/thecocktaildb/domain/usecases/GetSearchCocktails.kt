package com.renovavision.thecocktaildb.domain.usecases

import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.domain.repositories.SearchRepository
import javax.inject.Inject

class GetSearchCocktails @Inject constructor(private val searchRepo: SearchRepository) {

    suspend fun invoke(query: String): List<DrinkEntity> {
        val result = searchRepo.loadSearchResult(query)

        return result.map { DrinkEntity(it.strDrink, it.strDrinkThumb, it.key) }
    }
}