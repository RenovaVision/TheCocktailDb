package com.renovavision.thecocktaildb.category

import android.view.LayoutInflater
import android.view.ViewGroup
import com.renovavision.thecocktaildb.category.databinding.ItemViewCategoryBinding
import com.renovavision.thecocktaildb.network.DrinksCategory.Category
import com.renovavision.thecocktaildb.utils.BaseAdapter
import com.renovavision.thecocktaildb.utils.BaseViewHolder
import com.renovavision.thecocktaildb.utils.Dispatch

class CategoriesAdapter(dispatch: Dispatch) :
    BaseAdapter<Category, CategoriesAdapter.CategoryViewHolder>(dispatch) {

    override fun buildViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        ItemViewCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun areItemsTheSame(oldItem: Category, newItem: Category) =
        oldItem.strCategory == newItem.strCategory

    inner class CategoryViewHolder(private val binding: ItemViewCategoryBinding) :
        BaseViewHolder<Category>(binding.root) {

        override fun onCreate(dispatch: Dispatch) {
            super.onCreate(dispatch)
            itemView.setOnClickListener {
                item.let { dispatch.invoke(CategoryClicked(item)) }
            }
        }

        override fun onBind(item: Category) {
            binding.categoryName.text = item.strCategory
        }
    }
}