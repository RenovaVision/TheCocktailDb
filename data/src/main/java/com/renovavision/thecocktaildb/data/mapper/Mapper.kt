package com.renovavision.thecocktaildb.data.mapper

import com.renovavision.thecocktaildb.data.entities.CategoryEntity
import com.renovavision.thecocktaildb.data.entities.CocktailDetailsEntity
import com.renovavision.thecocktaildb.data.entities.CocktailEntity
import com.renovavision.thecocktaildb.data.entities.IngredientEntity
import com.renovavision.thecocktaildb.domain.entities.Category
import com.renovavision.thecocktaildb.domain.entities.Cocktail
import com.renovavision.thecocktaildb.domain.entities.CocktailDetails
import com.renovavision.thecocktaildb.domain.entities.Ingredient

typealias FunctionMapper<F, T> = ((from: F) -> T)

internal val categoryMapper: FunctionMapper<CategoryEntity, Category> = {
    Category(key = it.strCategory)
}

internal val ingredientMapper: FunctionMapper<IngredientEntity, Ingredient> = {
    Ingredient(key = it.strIngredient1)
}

internal val cocktailMapper: FunctionMapper<CocktailEntity, Cocktail> =
    {
        Cocktail(
            key = it.idDrink,
            strDrink = it.strDrink,
            strDrinkThumb = it.strDrinkThumb
        )
    }

internal val cocktailDetailsMapper: FunctionMapper<CocktailDetailsEntity, CocktailDetails> = {
    CocktailDetails(
        key = it.idDrink,
        strDrink = it.strDrink,
        strDrinkAlternate = it.strDrinkAlternate,
        strDrinkES = it.strDrinkES,
        strDrinkDE = it.strDrinkDE,
        strDrinkFR = it.strDrinkFR,
        strDrinkZH_HANS = it.strDrinkZH_HANS,
        strDrinkZH_HANT = it.strDrinkZH_HANT,
        strTags = it.strTags,
        strVideo = it.strVideo,
        strCategory = it.strCategory,
        strIBA = it.strIBA,
        strAlcoholic = it.strAlcoholic,
        strGlass = it.strGlass,
        strInstructions = it.strInstructions,
        strInstructionsES = it.strInstructionsES,
        strInstructionsDE = it.strInstructionsDE,
        strInstructionsFR = it.strInstructionsFR,
        strInstructionsZH_HANS = it.strInstructionsZH_HANS,
        strInstructionsZH_HANT = it.strInstructionsZH_HANT,
        strDrinkThumb = it.strDrinkThumb,
        strIngredient1 = it.strIngredient1,
        strIngredient2 = it.strIngredient2,
        strIngredient3 = it.strIngredient3,
        strIngredient4 = it.strIngredient4,
        strIngredient5 = it.strIngredient5,
        strIngredient6 = it.strIngredient6,
        strIngredient7 = it.strIngredient7,
        strIngredient8 = it.strIngredient8,
        strIngredient9 = it.strIngredient9,
        strIngredient10 = it.strIngredient10,
        strIngredient11 = it.strIngredient11,
        strIngredient12 = it.strIngredient12,
        strIngredient13 = it.strIngredient13,
        strIngredient14 = it.strIngredient14,
        strIngredient15 = it.strIngredient15,
        strMeasure1 = it.strMeasure1,
        strMeasure2 = it.strMeasure2,
        strMeasure3 = it.strMeasure3,
        strMeasure4 = it.strMeasure4,
        strMeasure5 = it.strMeasure5,
        strMeasure6 = it.strMeasure6,
        strMeasure7 = it.strMeasure7,
        strMeasure8 = it.strMeasure8,
        strMeasure9 = it.strMeasure9,
        strMeasure10 = it.strMeasure10,
        strMeasure11 = it.strMeasure11,
        strMeasure12 = it.strMeasure12,
        strMeasure13 = it.strMeasure13,
        strMeasure14 = it.strMeasure14,
        strMeasure15 = it.strMeasure15,
        strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
        dateModified = it.dateModified
    )
}