package com.renovavision.thecocktaildb.domain.entities

import java.io.Serializable

data class DrinksIngredientEntity(
    val drinks: List<IngredientEntity>
) : Serializable {

    data class IngredientEntity(
        override val key: String
    ) : Serializable,
        Indexed<String>
}