package com.taru.data.remote.plants.dto.inner

import com.squareup.moshi.Json
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

    @Json(name="common_name") val commonName: String?,
    @Json(name ="scientific_name") val scientificName: String?,
    @Json(name="image_url") val imageUrl: String?
) {
}