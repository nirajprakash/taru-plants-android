package com.taru.data.remote.plants.dto.inner

import com.squareup.moshi.JsonClass
import retrofit2.http.Field

/**
 * Created by Niraj on 30-01-2023.
 */

@JsonClass(generateAdapter = true)
data class PlantGrowthDto(
    @Field("ph_maximum") var phMaximum: Float?,
    @Field("ph_minimum") var phMinimum: Float?,
    @Field("light") var light: Int?,
    @Field("atmospheric_humidity") var atmosphericHumidity: Int?,
    @Field("soil_humidity") var soilHumidity: Float?,
    @Field("bloom_months") var bloomMonths: List<String>,
    @Field("growth_months") var growthMonths: List<String>,
    @Field("fruit_months") var fruitMonths: List<String>,
    @Field("soil_nutriments") var soilNutriments: Int?,
    @Field("soil_salinity") var soilSalinity: Int?

) {
}