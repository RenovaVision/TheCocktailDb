package com.renovavision.thecocktaildb.cocktails.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.renovavision.thecocktaildb.cocktails.databinding.ItemViewCocktailBinding
import com.renovavision.thecocktaildb.network.DrinksByQuery.Drink
import com.renovavision.thecocktaildb.utils.BaseAdapter
import com.renovavision.thecocktaildb.utils.BaseViewHolder
import com.renovavision.thecocktaildb.utils.Dispatch

class CocktailsAdapter(dispatch: Dispatch) :
    BaseAdapter<Drink, CocktailsAdapter.CocktailViewHolder>(dispatch) {

    override fun buildViewHolder(parent: ViewGroup, viewType: Int) =
        CocktailViewHolder(
            ItemViewCocktailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun areItemsTheSame(oldItem: Drink, newItem: Drink) =
        oldItem.key == newItem.key

    inner class CocktailViewHolder(private val binding: ItemViewCocktailBinding) :
        BaseViewHolder<Drink>(binding.root) {

        override fun onCreate(dispatch: Dispatch) {
            super.onCreate(dispatch)
            itemView.setOnClickListener {
                item.let { dispatch.invoke(CocktailClicked(item)) }
            }
        }

        override fun onBind(item: Drink) {
            super.onBind(item)

            binding.cocktailInfo.cocktail = item
        }
    }
}