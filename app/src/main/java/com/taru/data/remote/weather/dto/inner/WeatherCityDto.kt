package com.taru.data.remote.weather.dto.inner

import com.squareup.moshi.JsonClass

/**
 * Created by Niraj on 17-01-2023.
 */
@JsonClass(generateAdapter = true)
data class WeatherCityDto(val id: Int, val name: String,val timeZone: Int,
                          val coord: WeatherLocationDto) {
}