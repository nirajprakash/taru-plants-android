package com.taru.ui.pages.nav.plants.recent

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Niraj on 10-01-2023.
 */
class ModelRecent(var id: Int) {

    companion object {
        var diffCallback: DiffUtil.ItemCallback<ModelRecent> =
            object : DiffUtil.ItemCallback<ModelRecent>() {

                override fun areItemsTheSame(
                    oldItem: ModelRecent,
                    newItem: ModelRecent
                ): Boolean {
                    return oldItem.id == newItem.id                }

                override fun areContentsTheSame(
                    oldItem: ModelRecent,
                    newItem: ModelRecent
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}