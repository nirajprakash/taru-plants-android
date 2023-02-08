package com.taru.data.local.db.weather.inner

import com.squareup.moshi.Json

/**
 * Created by Niraj on 19-01-2023.
 */
data class WeatherAttrEntity(
    var temp: Float?,
    val pressure: Int,
    val humidity: Int, var tempMin: Float, var tempMax: Float
) {
}