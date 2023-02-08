package com.taru.domain.weather.repository

import com.taru.data.local.db.weather.WeatherCurrentRoomEntity
import com.taru.data.local.db.weather.WeatherForecastRoomData
import com.taru.domain.base.result.DomainResult
import com.taru.domain.weather.enitity.ModelWeather

/**
 * Created by Niraj on 16-01-2023.
 */
interface WeatherRepository {

    suspend fun getDetail(): DomainResult<WeatherCurrentRoomEntity>
    suspend fun getForecast(): DomainResult<WeatherForecastRoomData>

    suspend fun clearData(): DomainResult<Unit>
}