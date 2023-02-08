package com.taru.domain.weather.impl

import android.util.Log
import com.taru.data.base.local.LocalResult
import com.taru.data.base.remote.ApiResult
import com.taru.data.local.source.LocalLocationSource
import com.taru.data.local.source.LocalWeatherSource
import com.taru.data.local.db.weather.WeatherCurrentRoomEntity
import com.taru.data.local.db.weather.WeatherForecastRoomData
import com.taru.data.remote.ip.RemoteIpSource
import com.taru.data.remote.weather.RemoteWeatherSource
import com.taru.data.remote.weather.getEntries
import com.taru.data.remote.weather.toRoomEntity
import com.taru.domain.base.result.DomainResult
import com.taru.domain.weather.WeatherConstants
import com.taru.domain.weather.enitity.ModelWeather
import com.taru.domain.weather.repository.WeatherRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by Niraj on 16-01-2023.
 */

@ViewModelScoped
class DefaultWeatherRepository @Inject constructor(
    private val remoteIpSource: RemoteIpSource,

    private val remoteWeatherSource: RemoteWeatherSource,
    private val localLocationSource: LocalLocationSource,

    private val localWeatherSource: LocalWeatherSource
) : WeatherRepository {
    override suspend fun getDetail(): DomainResult<WeatherCurrentRoomEntity> {

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

        if (location == null) {
            return DomainResult.Failure(Throwable("Unable to save location"))

        }
        var date = (Date().time / 1000).toInt()

        Log.d("DefaultWeatherRepository", "getDetail date: $date location: $location")
        val weatherResult =
            if (location.weatherUnix != null && location.weatherUnix!! > date - WeatherConstants.CURRENT_REFRESH_PERIOD) {
                localWeatherSource.getLastCurrent(location.id)
            } else {
                localWeatherSource.removeCurrentForLocation(location.id)
                null
            }


        if (weatherResult != null && weatherResult is LocalResult.Success) {
            Log.d("DefaultWeatherRepository", "weatherResult : ${weatherResult.data}")

            return DomainResult.Success(weatherResult.data)

        }

        val apiResult = remoteWeatherSource.getCurrent(location.lat, location.lon)

        Log.d("DefaultWeatherRepository", "apiCall :")


        val result = when (apiResult) {
            is ApiResult.Success -> {
                Log.d("getDetail", "apiCall result: ${apiResult.data}")
                location.weatherUnix = apiResult.data.dt
                var weatherCurrent = apiResult.data.toRoomEntity(location.id)
                localWeatherSource.add(weatherCurrent)
                localLocationSource.update(location)
                DomainResult.Success(
                    weatherCurrent
                )

            }
            is ApiResult.Exception -> {
                DomainResult.Failure(apiResult.throwable)

            }
            is ApiResult.Message -> {
                DomainResult.Failure(Throwable("Not item found"))

            }
        }



        return result

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

    override suspend fun getForecast(): DomainResult<WeatherForecastRoomData> {
        var ipResult = remoteIpSource.getIp()
        if (ipResult !is ApiResult.Success) {
            return if (ipResult is ApiResult.Exception) {
                DomainResult.Failure(ipResult.throwable)
            } else {
                DomainResult.Failure(Throwable("F: location item found"))
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

        if (location == null) {
            return DomainResult.Failure(Throwable("F: Unable to save location"))

        }
        var date = (Date().time / 1000).toInt()

        Log.d("DefaultWeatherRepository", "F: getDetail date: $date location: $location")
        val weatherResult =
            if (location.forecastUnix != null && location.forecastUnix!! > date - WeatherConstants.FORECAST_REFRESH_PERIOD) {
                localWeatherSource.getLastForecast(location.id)
            } else {
                localWeatherSource.removeForecastForLocation(location.id)
                null
            }


        if (weatherResult != null && weatherResult is LocalResult.Success) {
            Log.d("DefaultWeatherRepository", "F: weatherResult : ${weatherResult.data}")

            return DomainResult.Success(weatherResult.data)

        }

        val apiResult = remoteWeatherSource.getForecast(location.lat, location.lon)

        Log.d("DefaultWeatherRepository", "F: apiCall :")

        if (apiResult is ApiResult.Success) {
            Log.d("getDetail", "F: apiCall result: ${apiResult.data}")
            var weatherForecast = apiResult.data.toRoomEntity(location.id)
            location.forecastUnix = weatherForecast.dt
            var idResult = localWeatherSource.addForecast(weatherForecast)
            /*if(id !is LocalResult.Success){
                DomainResult.Failure(Throwable("Not item found"))

            }*/
            localWeatherSource.addForecastEntries(apiResult.data.getEntries(idResult.data.toInt()))
            localLocationSource.update(location)
            var roomDataResult = localWeatherSource.getForecastById(idResult.data.toInt())

            if (roomDataResult is LocalResult.Success) {
                return DomainResult.Success(
                    roomDataResult.data
                )
            }

        }
        return if (apiResult is ApiResult.Exception) {
            DomainResult.Failure(apiResult.throwable)

        } else {
            DomainResult.Failure(Throwable("Not item found"))

        }




//DomainResult.Success(ModelWeather(0.1F, 0.2F))
    }
    override suspend fun clearData(): DomainResult.Success<Unit> {
        Log.d("DefaultWeatherRepository", "clearData:")
        localWeatherSource.removeAll()
//        withContext(Dispatchers.IO) { cachedRemoteKeySource.deleteAll() }
        return DomainResult.Success(Unit)
    }

}

