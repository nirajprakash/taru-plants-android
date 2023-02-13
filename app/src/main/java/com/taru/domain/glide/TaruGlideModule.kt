package com.taru.domain.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream


/**
 * Created by Niraj on 13-02-2023.
 */
@GlideModule
class TaruGlideModule: AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.prepend(
            CustomImageSizeModel::class.java,
            InputStream::class.java, CustomImageSizeUrlLoaderFactory()
        )
    }
}