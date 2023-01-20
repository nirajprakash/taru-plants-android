package com.taru.data.remote.weather.dto.inner

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Niraj on 17-01-2023.
 */
@JsonClass(generateAdapter = true)
data class WeatherDayTempDto(
    @Json(name = "temp_min") val tempMin: Float,
    @Json(name = "temp_max") val tempMax: Float, val temp: Float, val pressure: Int,
    val humidity: Int
) {
}