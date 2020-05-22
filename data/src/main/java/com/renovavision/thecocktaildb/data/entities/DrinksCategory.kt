package com.renovavision.thecocktaildb.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.renovavision.thecocktaildb.data.entities.DrinksCategory.Category.Companion.TABLE_NAME
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinksCategory(
    val drinks: List<Category>
) {

    @JsonClass(generateAdapter = true)
    @Entity(tableName = TABLE_NAME)
    data class Category(
        @PrimaryKey
        val strCategory: String
    ) {
        companion object {
            const val TABLE_NAME = "Categories"
        }
    }
}