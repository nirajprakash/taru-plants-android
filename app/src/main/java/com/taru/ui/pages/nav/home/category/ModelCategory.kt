package com.taru.ui.pages.nav.home.category

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Niraj on 09-01-2023.
 */
data class ModelCategory(var id: Int) {

    companion object {
        var diffCallback: DiffUtil.ItemCallback<ModelCategory> =
            object : DiffUtil.ItemCallback<ModelCategory>() {

                override fun areItemsTheSame(
                    oldItem: ModelCategory,
                    newItem: ModelCategory
                ): Boolean {
                    return oldItem.id == newItem.id                }

                override fun areContentsTheSame(
                    oldItem: ModelCategory,
                    newItem: ModelCategory
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}