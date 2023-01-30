package com.taru.data.remote.plants.dto.inner

import com.squareup.moshi.JsonClass
import retrofit2.http.Field

/**
 * Created by Niraj on 30-01-2023.
 */

@JsonClass(generateAdapter = true)
data class PlantImageDto(var id: Int,
                         @Field("image_url") var imageUrl: String ) {
}