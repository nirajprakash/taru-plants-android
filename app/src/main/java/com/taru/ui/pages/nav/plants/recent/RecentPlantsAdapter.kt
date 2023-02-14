package com.taru.ui.pages.nav.plants.recent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taru.data.local.db.plant.PlantEntity
import com.taru.databinding.NavPlantsItemBinding

/**
 * Created by Niraj on 14-02-2023.
 */
class RecentPlantsAdapter(val mOnClick: (PlantEntity) -> Unit): ListAdapter<PlantEntity, RecentPlantsAdapter.ItemViewHolder>(
    PlantEntity.diffCallback) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return viewHolder(parent, this)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(var binding: NavPlantsItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        fun bind(category: PlantEntity) {
//         binding.homeCategoryItemImage.load(R.drawable.pic_tool_category)
            binding.bModel = category
            binding.bOnClick =  this
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            var posi = absoluteAdapterPosition
            mOnClick(getItem(posi))
        }


    }

    companion object {
        fun viewHolder(parent: ViewGroup, adapter: RecentPlantsAdapter): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NavPlantsItemBinding.inflate(layoutInflater, parent, false)
            return adapter.ItemViewHolder(binding)
        }
    }
}