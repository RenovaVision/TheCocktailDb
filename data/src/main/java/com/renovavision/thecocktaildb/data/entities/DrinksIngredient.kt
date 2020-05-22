package com.renovavision.thecocktaildb.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.renovavision.thecocktaildb.data.entities.DrinksIngredient.Ingredient.Companion.TABLE_NAME
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinksIngredient(
    val drinks: List<Ingredient>
) {

    @JsonClass(generateAdapter = true)
    @Entity(tableName = TABLE_NAME)
    data class Ingredient(
        @PrimaryKey
        val strIngredient1: String
    ) {
        companion object {
            const val TABLE_NAME = "Ingredients"
        }
    }
}