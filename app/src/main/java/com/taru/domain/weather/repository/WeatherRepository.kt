package com.taru.domain.weather.repository

import com.taru.domain.base.result.DomainResult
import com.taru.domain.weather.enitity.ModelWeather

/**
 * Created by Niraj on 16-01-2023.
 */
interface WeatherRepository {

    suspend fun getDetail(): DomainResult<ModelWeather>
    suspend fun getForecast(): DomainResult<ModelWeather>
}