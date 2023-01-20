package com.taru.data.remote.weather

import com.taru.BuildConfig
import com.taru.data.remote.ip.dto.IpDto
import com.taru.data.remote.weather.dto.WeatherCurrentDto
import com.taru.data.remote.weather.dto.WeatherForecastDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Niraj on 17-01-2023.
 */
interface ApiWeather {


    @GET("weather")
    suspend fun getCurrent( @Query("lat") lat: Float,  @Query("lon") lon: Float,
                            @Query("appid") appId: String = BuildConfig.WEATHER_KEY
    ): Response<WeatherCurrentDto>

    @GET("forecast")
    suspend fun getForecast( @Query("lat") lat: Float,  @Query("lon") lon: Float,
//                             @Query("cnt") count: Int = 10,
                            @Query("appid") appId: String = BuildConfig.WEATHER_KEY
    ): Response<WeatherForecastDto>
}