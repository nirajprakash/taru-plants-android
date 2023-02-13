package com.taru.ui.pages.slider

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Niraj on 13-02-2023.
 */
data class ModelSliderImage(var id: Int, var image: String){

    companion object {
        var diffCallback: DiffUtil.ItemCallback<ModelSliderImage> =
            object : DiffUtil.ItemCallback<ModelSliderImage>() {

                override fun areItemsTheSame(
                    oldItem: ModelSliderImage,
                    newItem: ModelSliderImage
                ): Boolean {
                    return oldItem.id == newItem.id                }

                override fun areContentsTheSame(
                    oldItem: ModelSliderImage,
                    newItem: ModelSliderImage
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

}
