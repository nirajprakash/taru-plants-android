package com.taru.ui.pages.nav.plants.recent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.taru.R
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.databinding.HomeCategoryItemBinding
import com.taru.databinding.NavPlantsRecentItemBinding

/**
 * Created by Niraj on 10-01-2023.
 */
class RecentSearchAdapter(val mOnClick: (PlantRecentSearchEntity) -> Unit) : ListAdapter<PlantRecentSearchEntity, RecentSearchAdapter.ItemViewHolder>(PlantRecentSearchEntity.diffCallback) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return viewHolder(parent, this)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(var binding: NavPlantsRecentItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        fun bind(plantRecentSearchEntity: PlantRecentSearchEntity) {
//            binding.navPlantsRecentItemImage.load(R.drawable.pic_tool_category)
            binding.bOnClick =  this
            binding.bModel = plantRecentSearchEntity
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            var posi = absoluteAdapterPosition
            getItem(posi)?.let { mOnClick(it) }
        }


    }

    companion object {
        fun viewHolder(parent: ViewGroup, adapter: RecentSearchAdapter): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NavPlantsRecentItemBinding.inflate(layoutInflater, parent, false)
            return adapter.ItemViewHolder(binding)
        }
    }
}