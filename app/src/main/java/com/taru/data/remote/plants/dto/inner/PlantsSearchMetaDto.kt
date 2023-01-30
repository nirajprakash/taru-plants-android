package com.taru.data.remote.plants.dto.inner

import com.squareup.moshi.JsonClass

/**
 * Created by Niraj on 22-01-2023.
 */
@JsonClass(generateAdapter = true)
data class PlantsSearchMetaDto(
    val total: Int
) {
}