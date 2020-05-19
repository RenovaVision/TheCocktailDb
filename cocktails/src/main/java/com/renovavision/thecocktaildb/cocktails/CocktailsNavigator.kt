package com.renovavision.thecocktaildb.cocktails

import androidx.appcompat.widget.AppCompatImageView
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity
import java.lang.ref.WeakReference

interface CocktailsNavigator {
    fun navCocktailsListToDetails(
        cocktail: DrinksByQueryEntity.DrinkEntity,
        imageView: WeakReference<AppCompatImageView>
    )
}