package com.renovavision.thecocktaildb.domain.entities

import java.io.Serializable

data class Ingredient(override val key: String) : Serializable, Indexed<String>