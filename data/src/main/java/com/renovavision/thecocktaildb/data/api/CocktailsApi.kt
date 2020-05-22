package com.renovavision.thecocktaildb.data.api

import com.renovavision.thecocktaildb.data.entities.CocktailInfo
import com.renovavision.thecocktaildb.data.entities.DrinksByQuery
import com.renovavision.thecocktaildb.data.entities.DrinksCategory
import com.renovavision.thecocktaildb.data.entities.DrinksIngredient
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsApi {

    @GET("list.php?c=list")
    suspend fun loadDrinksCategory(): DrinksCategory

    @GET("list.php?i=list")
    suspend fun loadDrinksIngredient(): DrinksIngredient

    @GET("filter.php")
    suspend fun loadDrinksByCategory(@Query("c") category: String): DrinksByQuery

    @GET("filter.php")
    suspend fun loadDrinksByIngredient(@Query("i") ingredient: String): DrinksByQuery

    @GET("lookup.php")
    suspend fun loadCocktailInfoById(@Query("i") id: Int): CocktailInfo
}