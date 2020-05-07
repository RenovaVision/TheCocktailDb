package com.renovavision.thecocktaildb.domain.repositories

import com.renovavision.thecocktaildb.domain.entities.CocktailInfoEntity

interface SearchRepository {

    suspend fun loadSearchResult(query: String): List<CocktailInfoEntity.CocktailEntity>
}