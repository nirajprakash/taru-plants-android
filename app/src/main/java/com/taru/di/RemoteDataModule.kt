package com.taru.di

import com.taru.domain.weather.impl.DefaultWeatherRepository
import com.taru.domain.weather.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

/**
 * Created by Niraj on 16-01-2023.
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class RemoteDataModule {
    @Binds
    abstract fun bindWeatherRepository(impl: DefaultWeatherRepository): WeatherRepository

}