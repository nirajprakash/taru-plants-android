package com.taru.ui.pages.nav.plants.recommended

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Niraj on 10-01-2023.
 */
data class ModelPlant(var id: Int){

    companion object {
        var diffCallback: DiffUtil.ItemCallback<ModelPlant> =
            object : DiffUtil.ItemCallback<ModelPlant>() {

                override fun areItemsTheSame(
                    oldItem: ModelPlant,
                    newItem: ModelPlant
                ): Boolean {
                    return oldItem.id == newItem.id                }

                override fun areContentsTheSame(
                    oldItem: ModelPlant,
                    newItem: ModelPlant
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

}
