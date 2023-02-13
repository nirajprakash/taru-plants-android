package com.taru.domain.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelCache
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream
import javax.annotation.Nullable

/**
 * Created by Niraj on 13-02-2023.
 */
class CustomImageSizeUrlLoader(
    concreteLoader: ModelLoader<GlideUrl, InputStream> ,
     modelCache: ModelCache<CustomImageSizeModel, GlideUrl>): BaseGlideUrlLoader<CustomImageSizeModel>(concreteLoader, modelCache) {
    override fun handles(model: CustomImageSizeModel): Boolean {
    return true
    }

    override fun getUrl(
        model: CustomImageSizeModel?,
        width: Int,
        height: Int,
        options: Options?
    ): String? {
        return model?.requestCustomSizeUrl(width, height);
    }
}