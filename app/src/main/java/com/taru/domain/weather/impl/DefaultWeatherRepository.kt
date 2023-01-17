package com.taru.domain.weather.impl

import android.util.Log
import com.taru.data.base.remote.ApiResult
import com.taru.data.remote.ip.RemoteIpSource
import com.taru.data.remote.weather.RemoteWeatherSource
import com.taru.domain.base.result.DomainResult
import com.taru.domain.weather.enitity.ModelWeather
import com.taru.domain.weather.repository.WeatherRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * Created by Niraj on 16-01-2023.
 */

@ViewModelScoped
class DefaultWeatherRepository @Inject constructor(
    private val remoteIpSource: RemoteIpSource,

    private val remoteWeatherSource: RemoteWeatherSource
) : WeatherRepository {
    override suspend fun getDetail(): DomainResult<ModelWeather> {

        var ipResult = remoteIpSource.getIp()
        if (ipResult !is ApiResult.Success) {
            return if (ipResult is ApiResult.Exception) {
                DomainResult.Failure(ipResult.throwable)
            } else {
                DomainResult.Failure(Throwable("location item found"))
            }
        }

        // local Db Check for location

        var result = remoteWeatherSource.getCurrent(ipResult.data.lat, ipResult.data.lon)

        return when(result){
            is ApiResult.Success ->{
                Log.d("getDetail", "getDetail: ${result.data}")
                DomainResult.Success(ModelWeather(result.data.coord.lat, result.data.coord.lon))

            }
            is ApiResult.Exception ->{
                DomainResult.Failure(result.throwable)

            }
            is ApiResult.Message ->{
                DomainResult.Failure(Throwable("Not item found"))

            }
        }

//        return DomainResult.Success(ModelWeather(ipResult.data.lat, ipResult.data.lon))

        /* return when(val result = remoteIpSource.getIp()){
                 is ApiResult.Success ->{
                     DomainResult.Success(ModelWeather(result.data.lat, result.data.lon))

                 }
                 is ApiResult.Exception ->{
                     DomainResult.Failure(result.throwable)

                 }
                 is ApiResult.Message ->{
                     DomainResult.Failure(Throwable("Not item found"))

                 }
             }*/
    }

    override suspend fun getForecast(): DomainResult<ModelWeather> {
        return DomainResult.Success(ModelWeather(0.1, 0.2))
    }
}