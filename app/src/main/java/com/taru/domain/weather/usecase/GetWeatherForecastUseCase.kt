package com.taru.domain.weather.usecase

import com.taru.data.local.db.weather.WeatherForecastRoomData
import com.taru.domain.base.result.DomainResult
import com.taru.domain.weather.enitity.ModelWeather
import com.taru.domain.weather.repository.WeatherRepository
import javax.inject.Inject

/**
 * Created by Niraj on 16-01-2023.
 */
//private var weatherRepository: WeatherRepository
internal class GetWeatherForecastUseCase @Inject constructor(private var weatherRepository: WeatherRepository)  {
    suspend operator fun invoke(): DomainResult<WeatherForecastRoomData> = weatherRepository.getForecast()//DomainResult.Success(ModelWeather(0.1,0.2))//weatherRepository.getDetail()
}