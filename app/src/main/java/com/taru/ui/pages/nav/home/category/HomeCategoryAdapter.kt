package com.taru.ui.pages.nav.home.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.taru.R
import com.taru.data.local.assets.entities.ModelCategory
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.databinding.HomeCategoryItemBinding

/**
 * Created by Niraj on 09-01-2023.
 */
class HomeCategoryAdapter(val mOnClick: (ModelCategory) -> Unit): ListAdapter<ModelCategory, HomeCategoryAdapter.ItemViewHolder>(ModelCategory.diffCallback) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return viewHolder(parent, this)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(var binding: HomeCategoryItemBinding) : ViewHolder(binding.root),
        View.OnClickListener {
        fun bind(category: ModelCategory) {
//         binding.homeCategoryItemImage.load(R.drawable.pic_tool_category)
            binding.bModel = category
            binding.bOnClick = this
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            var posi = absoluteAdapterPosition
            mOnClick(getItem(posi))
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