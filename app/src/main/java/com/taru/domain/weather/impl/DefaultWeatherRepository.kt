package com.taru.domain.weather.impl

import android.util.Log
import com.taru.data.base.local.LocalResult
import com.taru.data.base.remote.ApiResult
import com.taru.data.local.db.location.LocalLocationSource
import com.taru.data.remote.ip.RemoteIpSource
import com.taru.data.remote.weather.RemoteWeatherSource
import com.taru.domain.base.result.DomainResult
import com.taru.domain.weather.enitity.ModelWeather
import com.taru.domain.weather.repository.WeatherRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.*
import javax.inject.Inject

/**
 * Created by Niraj on 16-01-2023.
 */

@ViewModelScoped
class DefaultWeatherRepository @Inject constructor(
    private val remoteIpSource: RemoteIpSource,

    private val remoteWeatherSource: RemoteWeatherSource,
    private val localLocationSource: LocalLocationSource
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

        val locationResult =
            localLocationSource.getLastNearest(ipResult.data.lat, ipResult.data.lon)

        val location = if (locationResult is LocalResult.Success) {
            locationResult.data
        } else {
            null
        }

        Log.d("DefaultWeatherRepository", "getDetail location: $location")

        val apiResult =
            if (location != null) {
                // TODO get local weather
                remoteWeatherSource.getCurrent(location.lat, location.lon)
            } else {
                remoteWeatherSource.getCurrent(ipResult.data.lat, ipResult.data.lon)
            }


        val result =  when (apiResult) {
            is ApiResult.Success -> {
                Log.d("getDetail", "getDetail: ${apiResult.data}")
                DomainResult.Success(ModelWeather(apiResult.data.coord.lat, apiResult.data.coord.lon))

            }
            is ApiResult.Exception -> {
                DomainResult.Failure(apiResult.throwable)

            }
            is ApiResult.Message -> {
                DomainResult.Failure(Throwable("Not item found"))

            }
        }
        if(location!=null){
            location.weatherUnix = (Date().time/1000).toInt()
            localLocationSource.update(location)
        }

        return  result

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