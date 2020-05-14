package com.renovavision.thecocktaildb.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.renovavision.thecocktaildb.domain.entities.CocktailInfoEntity.CocktailEntity
import com.renovavision.thecocktaildb.ui.R
import com.renovavision.thecocktaildb.ui.databinding.CocktailInfoViewBinding
import com.squareup.picasso.Picasso

class CocktailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = CocktailInfoViewBinding.inflate(LayoutInflater.from(context), this)

    @get:JvmSynthetic
    var info: CocktailEntity
        get() = throw UnsupportedOperationException()
        set(value) {
            Picasso.get()
                .load(value.strDrinkThumb)
                .placeholder(R.drawable.cocktail_placeholder)
                .error(R.drawable.error)
                .into(binding.cocktailImage)

            binding.categoryText.text = context.getString(R.string.category, value.strCategory)
            binding.alcoholicText.text = context.getString(R.string.alcoholic, value.strAlcoholic)
            binding.ingredientsText.text = value.getIngredients()
            binding.instructionText.text =
                context.getString(R.string.instruction, value.strInstructions)
        }
}