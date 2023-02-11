package com.taru.data.remote.plants.dto.inner

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Field

/**
 * Created by Niraj on 30-01-2023.
 */

@JsonClass(generateAdapter = true)
data class PlantSpeciesDto(var id: String,
                           @Json(name = "common_name" )  var name: String,
                           val edible: Boolean?,
                           @Json(name = "edible_part") val ediblePart: List<String> ?= listOf(),

                           val images : Map<String, List<PlantImageDto>>,
                           val growth: PlantGrowthDto,

                           val distribution: PlantDistributionDto

                           ) {
}