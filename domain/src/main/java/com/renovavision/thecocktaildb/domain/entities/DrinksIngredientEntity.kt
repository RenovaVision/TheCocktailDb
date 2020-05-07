package com.renovavision.thecocktaildb.domain.entities

import com.renovavision.thecocktaildb.domain.Indexed
import java.io.Serializable

data class DrinksIngredientEntity(
    val drinks: List<IngredientEntity>
) : Serializable {

    data class IngredientEntity(
        override val key: String
    ) : Serializable, Indexed<String>
}