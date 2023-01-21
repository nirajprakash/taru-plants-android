package com.taru.data.remote.weather

import com.taru.data.base.local.LocalResult
import com.taru.data.local.db.weather.ForecastEntryEntity
import com.taru.data.local.db.weather.inner.WeatherAttrEntity
import com.taru.data.local.db.weather.WeatherCurrentRoomEntity
import com.taru.data.local.db.weather.WeatherForecastRoomEntity
import com.taru.data.local.db.weather.inner.WeatherSubEntity
import com.taru.data.remote.weather.dto.WeatherCurrentDto
import com.taru.data.remote.weather.dto.WeatherForecastDto
import com.taru.domain.weather.WeatherConstants
import java.util.*

/**
 * Created by Niraj on 17-01-2023.
 */

fun WeatherCurrentDto.toRoomEntity(locationId: Int): WeatherCurrentRoomEntity {

    val first = weather.firstOrNull()
    val weather = first?.let { WeatherSubEntity(first.id, it.main) }
    return WeatherCurrentRoomEntity(
        visibility = visibility,

        cityId = id,
        weather = weather,
        locationId = locationId,
        dt = dt,
        attrs = WeatherAttrEntity(
            main.temp - WeatherConstants.KELVIN,
            main.pressure,
            main.humidity,
            main.tempMin - WeatherConstants.KELVIN,
            main.tempMax - WeatherConstants.KELVIN
        )
    )

}

fun WeatherForecastDto.toRoomEntity(locationId: Int): WeatherForecastRoomEntity {
    return WeatherForecastRoomEntity(

        count = cnt,
        locationId = locationId,
        dt = (Date().time / 1000).toInt()
    )
}


fun WeatherForecastDto.getEntries(id: Int): List<ForecastEntryEntity> {
    return list.map {

        val first = it.weather.firstOrNull()
        val weather = first?.let { it1 -> WeatherSubEntity(first.id, it1.main) }
        ForecastEntryEntity(
            forecastId = id, dt = it.dt, weather = weather, attrs = WeatherAttrEntity(
                tempMin = it.main.tempMin - WeatherConstants.KELVIN,
                tempMax = it.main.tempMax - WeatherConstants.KELVIN,
                humidity = it.main.humidity,
                pressure = it.main.pressure,
                temp = it.main.temp - WeatherConstants.KELVIN
            )
        )
    }.toList()

}
