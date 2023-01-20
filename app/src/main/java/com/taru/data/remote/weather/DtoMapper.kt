package com.taru.data.remote.weather

import com.taru.data.local.db.weather.inner.WeatherAttrEntity
import com.taru.data.local.db.weather.WeatherCurrentRoomEntity
import com.taru.data.local.db.weather.inner.WeatherSubEntity
import com.taru.data.remote.weather.dto.WeatherCurrentDto

/**
 * Created by Niraj on 17-01-2023.
 */

fun WeatherCurrentDto.toRoomEntity(locationId: Int) : WeatherCurrentRoomEntity {

    val first =weather.firstOrNull()
    val weather = first?.let { WeatherSubEntity(first.id, it.main) }
    return WeatherCurrentRoomEntity(
        visibility = visibility,

        cityId = id,
        weather = weather,
        locationId = locationId,
        dt= dt,
        attrs = WeatherAttrEntity(
            main.temp,
            main.pressure,
            main.humidity,
            main.tempMin,
            main.tempMax
        )
    )

}