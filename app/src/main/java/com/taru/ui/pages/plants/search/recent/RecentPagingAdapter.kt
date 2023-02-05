package com.taru.ui.pages.plants.search.recent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.databinding.SearchAutocompleteItemBinding

/**
 * Created by Niraj on 25-01-2023.
 */
class RecentPagingAdapter (val mOnClick: (PlantRecentSearchEntity) -> Unit) : PagingDataAdapter<PlantRecentSearchEntity,
        RecentPagingAdapter.ItemViewHolder>(
    PlantRecentSearchEntity.diffCallback) {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val entry = getItem(position) ?: return
        holder.bind(entry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return viewHolder(parent, this)
    }

    inner class ItemViewHolder(var binding: SearchAutocompleteItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(plantSearchEntryEntity: PlantRecentSearchEntity) {
//            binding.searchAutocompleteItemImage.load(R.drawable.pic_tool_category)
            binding.bOnClick =  this
            binding.bModel = plantSearchEntryEntity
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            var posi = absoluteAdapterPosition
            getItem(posi)?.let { mOnClick(it) }
        }


    }
    companion object {
        fun viewHolder(parent: ViewGroup, adapter: RecentPagingAdapter): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SearchAutocompleteItemBinding.inflate(layoutInflater, parent, false)
            return adapter.ItemViewHolder(binding)
        }
    }
}