package com.renovavision.thecocktaildb.domain.entities

import java.io.Serializable

data class DrinksCategoryEntity(
    val drinks: List<CategoryEntity>
) : Serializable {

    data class CategoryEntity(
        override val key: String
    ) : Serializable,
        Indexed<String>
}