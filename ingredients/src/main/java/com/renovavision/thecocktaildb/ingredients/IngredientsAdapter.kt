package com.renovavision.thecocktaildb.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import com.renovavision.thecocktaildb.domain.entities.Ingredient
import com.renovavision.thecocktaildb.ingredients.databinding.ItemViewIngredientBinding
import com.renovavision.thecocktaildb.ui.utils.BaseAdapter
import com.renovavision.thecocktaildb.ui.utils.BaseViewHolder
import com.renovavision.thecocktaildb.ui.uni.Dispatch

class IngredientsAdapter(dispatch: Dispatch) :
    BaseAdapter<Ingredient, IngredientsAdapter.IngredientViewHolder>(dispatch) {

    override fun buildViewHolder(parent: ViewGroup, viewType: Int) = IngredientViewHolder(
        ItemViewIngredientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient) =
        oldItem.key == newItem.key

    inner class IngredientViewHolder(private val binding: ItemViewIngredientBinding) :
        BaseViewHolder<Ingredient>(binding.root) {

        override fun onCreate(dispatch: Dispatch) {
            super.onCreate(dispatch)
            itemView.setOnClickListener {
                item.let { dispatch.invoke(IngredientClicked(item)) }
            }
        }

        override fun onBind(item: Ingredient) {
            binding.ingredientName.text = item.key
        }
    }
}