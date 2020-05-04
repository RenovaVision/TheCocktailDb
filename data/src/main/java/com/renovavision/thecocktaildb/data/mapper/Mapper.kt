package com.renovavision.thecocktaildb.data.mapper

import com.renovavision.thecocktaildb.data.entities.CocktailInfo
import com.renovavision.thecocktaildb.data.entities.DrinksByQuery
import com.renovavision.thecocktaildb.data.entities.DrinksCategory
import com.renovavision.thecocktaildb.data.entities.DrinksIngredient
import com.renovavision.thecocktaildb.domain.entities.CocktailInfoEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity

class Mapper {

    fun mapCategoryToEntity(data: DrinksCategory.Category): DrinksCategoryEntity.CategoryEntity =
        DrinksCategoryEntity.CategoryEntity(
            key = data.strCategory
        )

    fun mapIngredientToEntity(data: DrinksIngredient.Ingredient): DrinksIngredientEntity.IngredientEntity =
        DrinksIngredientEntity.IngredientEntity(
            key = data.strIngredient1
        )

    fun mapDrinkToEntity(data: DrinksByQuery.Drink): DrinksByQueryEntity.DrinkEntity =
        DrinksByQueryEntity.DrinkEntity(
            strDrink = data.strDrink,
            strDrinkThumb = data.strDrinkThumb,
            key = data.idDrink
        )

    fun mapCocktailToEntity(data: CocktailInfo.Cocktail): CocktailInfoEntity.CocktailEntity =
        CocktailInfoEntity.CocktailEntity(
            key = data.idDrink,
            strDrink = data.strDrink,
            strDrinkAlternate = data.strDrinkAlternate,
            strDrinkES = data.strDrinkES,
            strDrinkDE = data.strDrinkDE,
            strDrinkFR = data.strDrinkFR,
            strDrinkZH_HANS = data.strDrinkZH_HANS,
            strDrinkZH_HANT = data.strDrinkZH_HANT,
            strTags = data.strTags,
            strVideo = data.strVideo,
            strCategory = data.strCategory,
            strIBA = data.strIBA,
            strAlcoholic = data.strAlcoholic,
            strGlass = data.strGlass,
            strInstructions = data.strInstructions,
            strInstructionsES = data.strInstructionsES,
            strInstructionsDE = data.strInstructionsDE,
            strInstructionsFR = data.strInstructionsFR,
            strInstructionsZH_HANS = data.strInstructionsZH_HANS,
            strInstructionsZH_HANT = data.strInstructionsZH_HANT,
            strDrinkThumb = data.strDrinkThumb,
            strIngredient1 = data.strIngredient1,
            strIngredient2 = data.strIngredient2,
            strIngredient3 = data.strIngredient3,
            strIngredient4 = data.strIngredient4,
            strIngredient5 = data.strIngredient5,
            strIngredient6 = data.strIngredient6,
            strIngredient7 = data.strIngredient7,
            strIngredient8 = data.strIngredient8,
            strIngredient9 = data.strIngredient9,
            strIngredient10 = data.strIngredient10,
            strIngredient11 = data.strIngredient11,
            strIngredient12 = data.strIngredient12,
            strIngredient13 = data.strIngredient13,
            strIngredient14 = data.strIngredient14,
            strIngredient15 = data.strIngredient15,
            strMeasure1 = data.strMeasure1,
            strMeasure2 = data.strMeasure2,
            strMeasure3 = data.strMeasure3,
            strMeasure4 = data.strMeasure4,
            strMeasure5 = data.strMeasure5,
            strMeasure6 = data.strMeasure6,
            strMeasure7 = data.strMeasure7,
            strMeasure8 = data.strMeasure8,
            strMeasure9 = data.strMeasure9,
            strMeasure10 = data.strMeasure10,
            strMeasure11 = data.strMeasure11,
            strMeasure12 = data.strMeasure12,
            strMeasure13 = data.strMeasure13,
            strMeasure14 = data.strMeasure14,
            strMeasure15 = data.strMeasure15,
            strCreativeCommonsConfirmed = data.strCreativeCommonsConfirmed,
            dateModified = data.dateModified
        )
}