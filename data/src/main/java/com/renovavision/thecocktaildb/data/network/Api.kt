package com.renovavision.thecocktaildb.data.network

import com.renovavision.thecocktaildb.data.entities.CocktailDetailsResponse
import com.renovavision.thecocktaildb.data.entities.CocktailsResponse
import com.renovavision.thecocktaildb.data.entities.CategoriesResponse
import com.renovavision.thecocktaildb.data.entities.IngredientsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("list.php?c=list")
    suspend fun loadCategories(): CategoriesResponse

    @GET("list.php?i=list")
    suspend fun loadIngredients(): IngredientsResponse

    @GET("filter.php")
    suspend fun loadCocktailsByCategory(@Query("c") category: String): CocktailsResponse

    @GET("filter.php")
    suspend fun loadCocktailsByIngredient(@Query("i") ingredient: String): CocktailsResponse

    @GET("lookup.php")
    suspend fun loadCocktailInfoById(@Query("i") id: Int): CocktailDetailsResponse
}