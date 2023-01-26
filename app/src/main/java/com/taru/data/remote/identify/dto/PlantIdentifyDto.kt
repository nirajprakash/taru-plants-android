package com.taru.data.remote.identify.dto

import com.squareup.moshi.JsonClass
import com.taru.data.remote.identify.dto.inner.PlantIdentifiedResult

/**
 * Created by Niraj on 26-01-2023.
 */

@JsonClass(generateAdapter = true)
data class PlantIdentifyDto(val language: String,
val results:List<PlantIdentifiedResult>) {
}