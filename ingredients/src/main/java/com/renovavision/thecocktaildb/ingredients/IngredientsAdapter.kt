package com.renovavision.thecocktaildb.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import com.renovavision.thecocktaildb.ingredients.databinding.ItemViewIngredientBinding
import com.renovavision.thecocktaildb.network.DrinksIngredient.Ingredient
import com.renovavision.thecocktaildb.utils.BaseAdapter
import com.renovavision.thecocktaildb.utils.BaseViewHolder
import com.renovavision.thecocktaildb.utils.Dispatch

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
        oldItem.strIngredient1 == newItem.strIngredient1

    inner class IngredientViewHolder(private val binding: ItemViewIngredientBinding) :
        BaseViewHolder<Ingredient>(binding.root) {

        override fun onCreate(dispatch: Dispatch) {
            super.onCreate(dispatch)
            itemView.setOnClickListener {
                item.let { dispatch.invoke(IngredientClicked(item)) }
            }
        }

        override fun onBind(item: Ingredient) {
            binding.ingredientName.text = item.strIngredient1
        }
    }
}