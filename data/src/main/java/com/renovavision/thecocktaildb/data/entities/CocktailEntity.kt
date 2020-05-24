package com.renovavision.thecocktaildb.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.renovavision.thecocktaildb.data.entities.CocktailEntity.Companion.TABLE_NAME
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CocktailsResponse(
    val drinks: List<CocktailEntity>
)

@JsonClass(generateAdapter = true)
@Entity(tableName = TABLE_NAME)
data class CocktailEntity(
    @PrimaryKey
    val idDrink: Int,
    val strDrink: String,
    val strDrinkThumb: String,
    val category: String?,
    val ingredient: String?
) {
    companion object {
        const val TABLE_NAME = "Cocktails"
    }
}