package com.renovavision.thecocktaildb.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import com.renovavision.thecocktaildb.domain.entities.DrinksIngredientEntity.IngredientEntity
import com.renovavision.thecocktaildb.ingredients.databinding.ItemViewIngredientBinding
import com.renovavision.thecocktaildb.utils.BaseAdapter
import com.renovavision.thecocktaildb.utils.BaseViewHolder
import com.renovavision.thecocktaildb.utils.Dispatch

class IngredientsAdapter(dispatch: Dispatch) :
    BaseAdapter<IngredientEntity, IngredientsAdapter.IngredientViewHolder>(dispatch) {

    override fun buildViewHolder(parent: ViewGroup, viewType: Int) = IngredientViewHolder(
        ItemViewIngredientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun areItemsTheSame(oldItem: IngredientEntity, newItem: IngredientEntity) =
        oldItem.key == newItem.key

    inner class IngredientViewHolder(private val binding: ItemViewIngredientBinding) :
        BaseViewHolder<IngredientEntity>(binding.root) {

        override fun onCreate(dispatch: Dispatch) {
            super.onCreate(dispatch)
            itemView.setOnClickListener {
                item.let { dispatch.invoke(IngredientClicked(item)) }
            }
        }

        override fun onBind(item: IngredientEntity) {
            binding.ingredientName.text = item.key
        }
    }
}