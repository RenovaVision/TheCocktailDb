package com.renovavision.thecocktaildb.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.renovavision.thecocktaildb.home.SearchResultAdapter.SearchResultViewHolder
import com.renovavision.thecocktaildb.home.databinding.SearchItemBinding
import com.renovavision.thecocktaildb.network.DrinksByQuery.Drink
import com.renovavision.thecocktaildb.utils.BaseAdapter
import com.renovavision.thecocktaildb.utils.BaseViewHolder
import com.renovavision.thecocktaildb.utils.Dispatch

class SearchResultAdapter(dispatch: Dispatch) :
    BaseAdapter<Drink, SearchResultViewHolder>(dispatch) {

    override fun buildViewHolder(parent: ViewGroup, viewType: Int) = SearchResultViewHolder(
        SearchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun areItemsTheSame(oldItem: Drink, newItem: Drink) =
        oldItem.key == newItem.key

    inner class SearchResultViewHolder(private val binding: SearchItemBinding) :
        BaseViewHolder<Drink>(binding.root) {

        override fun onCreate(dispatch: Dispatch) {
            super.onCreate(dispatch)
            itemView.setOnClickListener {
                item.let { dispatch.invoke(CocktailClicked(item)) }
            }
        }

        override fun onBind(item: Drink) {
            binding.cocktailText.text = item.strDrink
        }
    }
}