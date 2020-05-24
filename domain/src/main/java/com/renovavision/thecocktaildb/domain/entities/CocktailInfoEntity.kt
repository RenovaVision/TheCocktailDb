package com.renovavision.thecocktaildb.domain.entities

import java.io.Serializable

data class CocktailInfoEntity(
    val drinks: List<CocktailEntity>
) : Serializable {

    data class CocktailEntity(
        override val key: Int,
        val strDrink: String,
        val strDrinkAlternate: String?,
        val strDrinkES: String?,
        val strDrinkDE: String?,
        val strDrinkFR: String?,
        val strDrinkZH_HANS: String?,
        val strDrinkZH_HANT: String?,
        val strTags: String?,
        val strVideo: String?,
        val strCategory: String,
        val strIBA: String?,
        val strAlcoholic: String,
        val strGlass: String?,
        val strInstructions: String,
        val strInstructionsES: String?,
        val strInstructionsDE: String?,
        val strInstructionsFR: String?,
        val strInstructionsZH_HANS: String?,
        val strInstructionsZH_HANT: String?,
        val strDrinkThumb: String,
        val strIngredient1: String?,
        val strIngredient2: String?,
        val strIngredient3: String?,
        val strIngredient4: String?,
        val strIngredient5: String?,
        val strIngredient6: String?,
        val strIngredient7: String?,
        val strIngredient8: String?,
        val strIngredient9: String?,
        val strIngredient10: String?,
        val strIngredient11: String?,
        val strIngredient12: String?,
        val strIngredient13: String?,
        val strIngredient14: String?,
        val strIngredient15: String?,
        val strMeasure1: String?,
        val strMeasure2: String?,
        val strMeasure3: String?,
        val strMeasure4: String?,
        val strMeasure5: String?,
        val strMeasure6: String?,
        val strMeasure7: String?,
        val strMeasure8: String?,
        val strMeasure9: String?,
        val strMeasure10: String?,
        val strMeasure11: String?,
        val strMeasure12: String?,
        val strMeasure13: String?,
        val strMeasure14: String?,
        val strMeasure15: String?,
        val strCreativeCommonsConfirmed: String?,
        val dateModified: String?
    ) : Serializable,
        Indexed<Int> {

        fun getIngredients(): String {
            val list = listOf(
                makeIngredient(strIngredient1, strMeasure1),
                makeIngredient(strIngredient2, strMeasure2),
                makeIngredient(strIngredient3, strMeasure3),
                makeIngredient(strIngredient4, strMeasure4),
                makeIngredient(strIngredient5, strMeasure5),
                makeIngredient(strIngredient6, strMeasure6),
                makeIngredient(strIngredient7, strMeasure7),
                makeIngredient(strIngredient8, strMeasure8),
                makeIngredient(strIngredient9, strMeasure9),
                makeIngredient(strIngredient10, strMeasure10),
                makeIngredient(strIngredient11, strMeasure11),
                makeIngredient(strIngredient12, strMeasure12),
                makeIngredient(strIngredient13, strMeasure13),
                makeIngredient(strIngredient14, strMeasure14),
                makeIngredient(strIngredient15, strMeasure15)
            )

            return list.filter { !it.isNullOrEmpty() }.joinToString(", ")
        }

        private fun makeIngredient(ingredient: String?, measure: String?) =
            if (measure.isNullOrEmpty()) {
                ingredient
            } else {
                "$ingredient - $measure"
            }
    }
}