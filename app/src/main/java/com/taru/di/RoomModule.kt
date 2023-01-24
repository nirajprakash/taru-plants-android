package com.taru.di

import android.content.Context
import androidx.room.Room
import com.taru.data.local.db.AppDatabase
import com.taru.data.local.db.DatabaseConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Niraj on 18-01-2023.
 */

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DatabaseConstants.DATABASE_NAME
    ).fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideLocationDao(db: AppDatabase) = db.locationDao()


    @Singleton
    @Provides
    fun provideWeatherCurrentDao(db: AppDatabase) = db.weatherCurrent()

    @Singleton
    @Provides
    fun provideWeatherForecastDao(db: AppDatabase) = db.weatherForecast()

    @Singleton
    @Provides
    fun provideWeatherForecastEntry(db: AppDatabase) = db.weatherForecastEntry()


    @Singleton
    @Provides
    fun provideCachedRemoteKey(db: AppDatabase) = db.cachedRemoteKey()



    @Singleton
    @Provides
    fun providePlantsSearchDao(db: AppDatabase) = db.plantsSearchDao()
}