package com.taru.data.remote.plants.dto.inner

import com.squareup.moshi.JsonClass
import retrofit2.http.Field

/**
 * Created by Niraj on 22-01-2023.
 */
@JsonClass(generateAdapter = true)
data class PlantsSearchEntryDto(
    val id: Int,
    val family: String,
    val slug: String,

    @Field("common_name") val commonName: String,
    @Field("scientific_name") val scientificName: String,
    @Field("image_url") val imageUrl: String
) {
}