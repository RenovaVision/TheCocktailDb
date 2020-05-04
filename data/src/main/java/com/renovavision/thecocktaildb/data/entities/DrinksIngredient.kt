package com.renovavision.thecocktaildb.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinksIngredient(
    val drinks: List<Ingredient>
) {

    @JsonClass(generateAdapter = true)
    data class Ingredient(
        val strIngredient1: String
    )
}