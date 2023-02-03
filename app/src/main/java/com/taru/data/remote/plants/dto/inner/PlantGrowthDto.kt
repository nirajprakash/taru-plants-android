package com.taru.data.remote.plants.dto.inner

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Field

/**
 * Created by Niraj on 30-01-2023.
 */

@JsonClass(generateAdapter = true)
data class PlantGrowthDto(
    @Json(name = "ph_maximum") var phMaximum: Float?,
    @Json(name = "ph_minimum") var phMinimum: Float?,
    @Json(name = "light") var light: Int?,
    @Json(name = "atmospheric_humidity") var atmosphericHumidity: Float?,
    @Json(name = "soil_humidity") var soilHumidity: Float?,
    @Json(name = "bloom_months") var bloomMonths: List<String>? = listOf(),
    @Json(name = "growth_months") var growthMonths: List<String>? = listOf(),
    @Json(name = "fruit_months") var fruitMonths: List<String>? = listOf(),
    @Json(name = "soil_nutriments") var soilNutriments: Int?,
    @Json(name = "soil_salinity") var soilSalinity: Int?

) {
}