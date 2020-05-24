package com.renovavision.thecocktaildb.domain.entities

import java.io.Serializable

data class Cocktail(
    override val key: Int,
    val strDrink: String,
    val strDrinkThumb: String
) : Serializable, Indexed<Int>