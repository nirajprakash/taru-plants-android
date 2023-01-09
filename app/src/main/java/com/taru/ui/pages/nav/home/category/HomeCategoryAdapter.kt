package com.taru.ui.pages.nav.home.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.taru.R
import com.taru.databinding.HomeCategoryItemBinding

/**
 * Created by Niraj on 09-01-2023.
 */
class HomeCategoryAdapter: ListAdapter<ModelCategory, HomeCategoryAdapter.ItemViewHolder>(ModelCategory.diffCallback) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return viewHolder(parent, this)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(var binding: HomeCategoryItemBinding) : ViewHolder(binding.root){
        fun bind(category: ModelCategory) {
         binding.homeCategoryItemImage.load(R.drawable.pic_tool_category)

        }


    }

    companion object {
        fun viewHolder(parent: ViewGroup, adapter: HomeCategoryAdapter): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = HomeCategoryItemBinding.inflate(layoutInflater, parent, false)
            return adapter.ItemViewHolder(binding)
        }
    }
}