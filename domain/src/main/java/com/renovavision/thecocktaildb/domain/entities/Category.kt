package com.renovavision.thecocktaildb.domain.entities

import java.io.Serializable

data class Category(override val key: String) : Serializable, Indexed<String>