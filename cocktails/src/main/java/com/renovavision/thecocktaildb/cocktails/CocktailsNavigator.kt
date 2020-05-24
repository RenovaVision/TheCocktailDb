package com.renovavision.thecocktaildb.cocktails

import androidx.appcompat.widget.AppCompatImageView
import com.renovavision.thecocktaildb.domain.entities.Cocktail
import java.lang.ref.WeakReference

interface CocktailsNavigator {
    fun navCocktailsListToDetails(cocktail: Cocktail, imageView: WeakReference<AppCompatImageView>)
}