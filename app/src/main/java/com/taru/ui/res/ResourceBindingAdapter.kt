package com.taru.ui.res

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

/**
 * Created by Niraj on 28-01-2023.
 */
object ResourceBindingAdapter {
    @BindingAdapter("app:bindCoilSrc")
    @JvmStatic
    fun setBindCoilSrc(view: ImageView, src: Uri?) {
        // Important to break potential infinite loops.
//        info { "loadinhg $src" }
        src?.let {
            view.load(it) //.into(view)
        }
    }
}