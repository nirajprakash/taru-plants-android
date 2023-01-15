package com.taru.ui.pages.search.plants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.taru.R
import com.taru.databinding.SearchPlantsItemBinding
import com.taru.ui.pages.nav.plants.recommended.ModelPlant

/**
 * Created by Niraj on 15-01-2023.
 */
class SearchPlantsAdapter( val mOnClick: (ModelPlant) -> Unit) : ListAdapter<ModelPlant, SearchPlantsAdapter.ItemViewHolder>(
    ModelPlant.diffCallback) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return viewHolder(parent, this)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(var binding: SearchPlantsItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        fun bind(plant: ModelPlant) {
            binding.searchPlantsItemImage.load(R.drawable.pic_tool_category)
            binding.bOnClick =  this
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            var posi = adapterPosition
            mOnClick(getItem(posi))
        }


    }

    companion object {
        fun viewHolder(parent: ViewGroup, adapter: SearchPlantsAdapter): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SearchPlantsItemBinding.inflate(layoutInflater, parent, false)
            return adapter.ItemViewHolder(binding)
        }
    }
}