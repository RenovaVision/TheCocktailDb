package com.renovavision.thecocktaildb.domain.entities

import com.renovavision.thecocktaildb.domain.Indexed
import java.io.Serializable

data class DrinksByQueryEntity(
    val drinks: List<DrinkEntity>
) : Serializable {

    data class DrinkEntity(
        val strDrink: String,
        val strDrinkThumb: String,
        override val key: Int
    ) : Serializable, Indexed<Int>
}