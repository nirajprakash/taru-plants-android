package com.taru.data.local.assets.entities

import androidx.recyclerview.widget.DiffUtil
import com.taru.ui.pages.nav.home.category.DepreModelCategory

/**
 * Created by Niraj on 06-02-2023.
 */
data class ModelCategory(
    val id : Int,
    var filterForEdible: Boolean,
    var imageSrc: String,
    var q: String,
    var title: String
) {

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