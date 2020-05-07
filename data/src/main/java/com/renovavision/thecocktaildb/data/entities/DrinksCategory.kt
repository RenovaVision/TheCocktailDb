package com.renovavision.thecocktaildb.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinksCategory(
    val drinks: List<Category>
) {

    @JsonClass(generateAdapter = true)
    data class Category(
        val strCategory: String
    )
}