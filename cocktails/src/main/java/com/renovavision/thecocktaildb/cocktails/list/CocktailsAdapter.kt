package com.renovavision.thecocktaildb.cocktails.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.renovavision.thecocktaildb.cocktails.databinding.ItemViewCocktailBinding
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity.DrinkEntity
import com.renovavision.thecocktaildb.ui.utils.BaseAdapter
import com.renovavision.thecocktaildb.ui.utils.BaseViewHolder
import com.renovavision.thecocktaildb.ui.utils.Dispatch
import java.lang.ref.WeakReference

class CocktailsAdapter(dispatch: Dispatch) :
    BaseAdapter<DrinkEntity, CocktailsAdapter.CocktailViewHolder>(dispatch) {

    override fun buildViewHolder(parent: ViewGroup, viewType: Int) =
        CocktailViewHolder(
            ItemViewCocktailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun areItemsTheSame(oldItem: DrinkEntity, newItem: DrinkEntity) =
        oldItem.key == newItem.key

    inner class CocktailViewHolder(private val binding: ItemViewCocktailBinding) :
        BaseViewHolder<DrinkEntity>(binding.root) {

        override fun onCreate(dispatch: Dispatch) {
            super.onCreate(dispatch)
            itemView.setOnClickListener {
                item.let {
                    dispatch.invoke(
                        CocktailClicked(
                            item,
                            WeakReference(binding.cocktailInfo.posterView)
                        )
                    )
                }
            }
        }

        override fun onBind(item: DrinkEntity) {
            super.onBind(item)

            binding.cocktailInfo.cocktail = item
        }
    }
}