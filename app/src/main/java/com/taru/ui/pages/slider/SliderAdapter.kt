package com.taru.ui.pages.slider

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.github.piasy.biv.view.GlideImageViewFactory
import com.github.piasy.biv.view.ImageViewFactory
import com.taru.databinding.SliderImageItemBinding


/**
 * Created by Niraj on 13-02-2023.
 */
class SliderAdapter: ListAdapter<ModelSliderImage, SliderAdapter.ItemViewHolder>(ModelSliderImage.diffCallback) {

    private val mViewFactory: ImageViewFactory = GlideImageViewFactory()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return viewHolder(parent, this)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: ItemViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    override fun onViewAttachedToWindow(holder: ItemViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder.hasNoImage()) {
            holder.rebind();
        }
    }
    inner class ItemViewHolder(var binding: SliderImageItemBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.sliderImageItemImage.setImageViewFactory(mViewFactory)
        }
        fun bind(model: ModelSliderImage) {
//         binding.homeCategoryItemImage.load(R.drawable.pic_tool_category)
//            binding.bModel = model
//            binding.executePendingBindings()


            Log.d("SliderAdapter bind", "bind: ${model.image}")
//            imageUrl = imageUrl
            binding.sliderImageItemImage.showImage(Uri.parse(model.image))
        }
 
        fun rebind() {
            binding.sliderImageItemImage.showImage(Uri.parse(getItem(absoluteAdapterPosition).image))
        }

        fun clear() {
            val ssiv: SubsamplingScaleImageView? = binding.sliderImageItemImage.ssiv
            ssiv?.recycle()
        }

        fun hasNoImage(): Boolean {
            val ssiv: SubsamplingScaleImageView? = binding.sliderImageItemImage.ssiv
            return ssiv == null || !ssiv.hasImage();
        }


    }
    companion object {
        fun viewHolder(parent: ViewGroup, adapter: SliderAdapter): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SliderImageItemBinding.inflate(layoutInflater, parent, false)
            return adapter.ItemViewHolder(binding)
        }
    }


}