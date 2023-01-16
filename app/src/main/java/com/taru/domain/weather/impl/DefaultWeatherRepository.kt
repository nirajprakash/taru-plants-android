package com.taru.domain.weather.impl

import com.taru.data.base.remote.ApiResult
import com.taru.data.remote.ip.RemoteIpSource
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
  private val remoteIpSource: RemoteIpSource
): WeatherRepository {
    override suspend fun getDetail(): DomainResult<ModelWeather> {
        return when(val result = remoteIpSource.getIp()){
            is ApiResult.Success ->{
                DomainResult.Success(ModelWeather(result.data.lat, result.data.lon))

            }
            is ApiResult.Exception ->{
                DomainResult.Failure(result.throwable)

            }
            is ApiResult.Message ->{
                DomainResult.Failure(Throwable("Not item found"))

            }
        }
    }

    override suspend fun getForecast(): DomainResult<ModelWeather> {
        return DomainResult.Success(ModelWeather(0.1, 0.2))
    }
}