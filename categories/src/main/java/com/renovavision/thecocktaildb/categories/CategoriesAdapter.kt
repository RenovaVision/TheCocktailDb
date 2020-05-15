package com.renovavision.thecocktaildb.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.renovavision.thecocktaildb.categories.databinding.ItemViewCategoryBinding
import com.renovavision.thecocktaildb.domain.entities.DrinksCategoryEntity.CategoryEntity
import com.renovavision.thecocktaildb.ui.utils.BaseAdapter
import com.renovavision.thecocktaildb.ui.utils.BaseViewHolder
import com.renovavision.thecocktaildb.ui.utils.Dispatch

class CategoriesAdapter(dispatch: Dispatch) :
    BaseAdapter<CategoryEntity, CategoriesAdapter.CategoryViewHolder>(dispatch) {

    override fun buildViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        ItemViewCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun areItemsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity) =
        oldItem.key == newItem.key

    inner class CategoryViewHolder(private val binding: ItemViewCategoryBinding) :
        BaseViewHolder<CategoryEntity>(binding.root) {

        override fun onCreate(dispatch: Dispatch) {
            super.onCreate(dispatch)
            itemView.setOnClickListener {
                item.let { dispatch.invoke(CategoryClicked(item)) }
            }
        }

        override fun onBind(item: CategoryEntity) {
            binding.categoryName.text = item.key
        }
    }
}