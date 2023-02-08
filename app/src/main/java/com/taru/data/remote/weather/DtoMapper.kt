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
import kotlin.math.max
import kotlin.math.min

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

    var result = mutableListOf<ForecastEntryEntity>()

    var calender = Calendar.getInstance()

    var currentDay = calender.get(Calendar.DAY_OF_MONTH)

    var entry : ForecastEntryEntity? = null

    var counter = 0
//  TODO adjust weather  var weather
    for (forecastDay in list){

        calender.time = Date(forecastDay.dt*1000L)
        var day =  calender.get(Calendar.DAY_OF_MONTH)
        if(day>currentDay){
            if(entry!=null){

                entry.attrs.temp = entry.attrs.temp?.div(counter)
                result.add(entry)
                counter =0
                entry = null
            }
            currentDay = day

        }
        val first = forecastDay.weather.firstOrNull()
        val weather = first?.let { it1 -> WeatherSubEntity(first.id, it1.main) }

        val tempMin =forecastDay.main.tempMin - WeatherConstants.KELVIN
        val tempMax =forecastDay.main.tempMax - WeatherConstants.KELVIN
        val tempAvg =forecastDay.main.temp - WeatherConstants.KELVIN

        if(entry == null){
            counter = 1
            entry =  ForecastEntryEntity(
                forecastId = id, dt = forecastDay.dt, weather = weather, attrs = WeatherAttrEntity(
                    tempMin = tempMin,
                    tempMax = tempMax,
                    humidity = forecastDay.main.humidity,
                    pressure = forecastDay.main.pressure,
                    temp = tempAvg
                )
            )
        }else{
            counter++
            entry.attrs.temp = tempAvg + (entry.attrs.temp?:0f)
            entry.attrs.tempMin = min(tempMin , (entry.attrs.tempMin?:0f))
            entry.attrs.tempMax = max(tempMin , (entry.attrs.tempMax?:0f))
        }


    }

    return result
/*    return list.map {

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
    }.toList()*/

}
