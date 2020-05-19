package com.renovavision.thecocktaildb.data

import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Assert.assertEquals
import org.junit.Test

class CocktailsApiTests : BaseApiTest() {

    @Test
    fun getIngredientsTest() = runBlocking {
        enqueueResponse("ingredients.json")

        val ingredients = cocktailsService.loadDrinksIngredient().drinks

        assertThat(ingredients, notNullValue())
        assertEquals(13, ingredients.size)
        assertEquals("Sweet Vermouth", ingredients[4].strIngredient1)
    }

    @Test
    fun getCategoriesTest() = runBlocking {
        enqueueResponse("categories.json")

        val categories = cocktailsService.loadDrinksCategory().drinks

        assertThat(categories, notNullValue())
        assertEquals(11, categories.size)
        assertEquals("Cocktail", categories[1].strCategory)
    }

    @Test
    fun getCocktailsByCategory() = runBlocking {
        enqueueResponse("cocktailsByCategory.json")

        val cocktails = cocktailsService.loadDrinksByCategory("cocktail").drinks

        assertThat(cocktails, notNullValue())
        assertEquals(9, cocktails.size)
        assertEquals(17222, cocktails[7].idDrink)
        assertEquals("A1", cocktails[7].strDrink)
    }

    @Test
    fun getCocktailsByIngredient() = runBlocking {
        enqueueResponse("cocktailsByIngredient.json")

        val cocktails = cocktailsService.loadDrinksByIngredient("vodka").drinks

        assertThat(cocktails, notNullValue())
        assertEquals(7, cocktails.size)
        assertEquals(178318, cocktails[3].idDrink)
        assertEquals("747 Drink", cocktails[3].strDrink)
    }

    @Test
    fun getCocktailInfo() = runBlocking {
        enqueueResponse("cocktailInfo.json")

        val cocktailInfo = cocktailsService.loadCocktailInfoById(11007).drinks

        assertThat(cocktailInfo, notNullValue())
        assertEquals(11007, cocktailInfo[0].idDrink)
        assertEquals("Margarita", cocktailInfo[0].strDrink)
        assertEquals("Ordinary Drink", cocktailInfo[0].strCategory)
        assertEquals("Alcoholic", cocktailInfo[0].strAlcoholic)
    }

    @Test
    fun getSearchResults() = runBlocking {
        enqueueResponse("searchResult.json")

        val searchResult = cocktailsService.searchCocktails("marg").drinks

        assertThat(searchResult, notNullValue())
        assertEquals(5, searchResult.size)
        assertEquals(12322, searchResult[4].idDrink)
        assertEquals("Strawberry Margarita", searchResult[4].strDrink)
        assertEquals("Ordinary Drink", searchResult[4].strCategory)
        assertEquals("Alcoholic", searchResult[4].strAlcoholic)
    }
}