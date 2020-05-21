package com.renovavision.thecocktaildb.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.ui.R
import com.renovavision.thecocktaildb.ui.databinding.CocktailViewBinding
import com.squareup.picasso.Picasso

class CocktailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = CocktailViewBinding.inflate(LayoutInflater.from(context), this)

    @get:JvmSynthetic
    var cocktail: DrinkEntity
        get() = throw UnsupportedOperationException()
        set(value) {
            binding.cocktailName.text = value.strDrink

            ViewCompat.setTransitionName(binding.cocktailPoster, value.strDrinkThumb)
            Picasso.get()
                .load(value.strDrinkThumb)
                .placeholder(R.drawable.cocktail_placeholder)
                .error(R.drawable.error)
                .into(binding.cocktailPoster)
        }

    val posterView: AppCompatImageView
        get() = binding.cocktailPoster
}