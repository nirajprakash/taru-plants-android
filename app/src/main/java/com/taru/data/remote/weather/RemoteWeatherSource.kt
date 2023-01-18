package com.taru.data.remote.weather

import com.taru.data.base.remote.handleApi
import com.taru.data.remote.ip.ApiIp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Niraj on 17-01-2023.
 */
class RemoteWeatherSource @Inject constructor(
    var apiWeather: ApiWeather
){

    suspend fun  getCurrent(lat: Float, lon: Float) = withContext(Dispatchers.IO) {

        return@withContext handleApi { apiWeather.getCurrent(lat, lon) }


    }

    suspend fun  getForecast(lat: Float, lon: Float) = withContext(Dispatchers.IO) {

        return@withContext handleApi { apiWeather.getForecast(lat, lon) }


    }
}