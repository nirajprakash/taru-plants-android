package com.taru.ui.pages.nav.home.category

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Niraj on 09-01-2023.
 */
data class DepreModelCategory(var id: Int) {

    companion object {
        var diffCallback: DiffUtil.ItemCallback<DepreModelCategory> =
            object : DiffUtil.ItemCallback<DepreModelCategory>() {

                override fun areItemsTheSame(
                    oldItem: DepreModelCategory,
                    newItem: DepreModelCategory
                ): Boolean {
                    return oldItem.id == newItem.id                }

                override fun areContentsTheSame(
                    oldItem: DepreModelCategory,
                    newItem: DepreModelCategory
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}