package com.taru.data.remote.plants.dto

import com.squareup.moshi.JsonClass

/**
 * Created by Niraj on 03-02-2023.
 */

@JsonClass(generateAdapter = true)
data class PlantDetailResponseDto(val data: PlantDetailDto) {
}