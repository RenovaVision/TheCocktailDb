package com.renovavision.thecocktaildb.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.home.SearchResultAdapter.SearchResultViewHolder
import com.renovavision.thecocktaildb.home.databinding.SearchItemBinding
import com.renovavision.thecocktaildb.utils.BaseAdapter
import com.renovavision.thecocktaildb.utils.BaseViewHolder
import com.renovavision.thecocktaildb.utils.Dispatch

class SearchResultAdapter(dispatch: Dispatch) :
    BaseAdapter<DrinkEntity, SearchResultViewHolder>(dispatch) {

    override fun buildViewHolder(parent: ViewGroup, viewType: Int) = SearchResultViewHolder(
        SearchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun areItemsTheSame(oldItem: DrinkEntity, newItem: DrinkEntity) =
        oldItem.key == newItem.key

    inner class SearchResultViewHolder(private val binding: SearchItemBinding) :
        BaseViewHolder<DrinkEntity>(binding.root) {

        override fun onCreate(dispatch: Dispatch) {
            super.onCreate(dispatch)
            itemView.setOnClickListener {
                item.let { dispatch.invoke(CocktailClicked(item)) }
            }
        }

        override fun onBind(item: DrinkEntity) {
            binding.cocktailText.text = item.strDrink
        }
    }
}