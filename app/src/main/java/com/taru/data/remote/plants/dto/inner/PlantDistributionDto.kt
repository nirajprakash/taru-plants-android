package com.taru.data.remote.plants.dto.inner

import com.squareup.moshi.JsonClass

/**
 * Created by Niraj on 11-02-2023.
 */

@JsonClass(generateAdapter = true)
data class PlantDistributionDto(var native: List<String>) {
}