package com.taru.data.remote.weather.dto

import com.squareup.moshi.JsonClass
import com.taru.data.remote.weather.dto.inner.WeatherDto
import com.taru.data.remote.weather.dto.inner.WeatherLocationDto
import com.taru.data.remote.weather.dto.inner.WeatherMainDto

/**
 * Created by Niraj on 17-01-2023.
 */
@JsonClass(generateAdapter = true)
data class WeatherCurrentDto(
    val id: Int,
    val coord: WeatherLocationDto,
    val weather: List<WeatherDto>,
    val main: WeatherMainDto,
    val visibility: Int,
    val dt: Int
) {
}