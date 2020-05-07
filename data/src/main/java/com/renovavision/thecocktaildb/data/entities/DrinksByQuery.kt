package com.renovavision.thecocktaildb.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinksByQuery(
    val drinks: List<Drink>
) {

    @JsonClass(generateAdapter = true)
    data class Drink(
        val strDrink: String,
        val strDrinkThumb: String,
        val idDrink: Int
    )
}