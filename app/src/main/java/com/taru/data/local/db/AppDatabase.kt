package com.taru.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taru.data.local.db.location.LocationRoomDao
import com.taru.data.local.db.location.LocationRoomEntity
import com.taru.data.local.db.weather.WeatherCurrentDao
import com.taru.data.local.db.weather.WeatherCurrentRoomEntity

/**
 * Created by Niraj on 18-01-2023.
 */
@Database(entities = [LocationRoomEntity::class, WeatherCurrentRoomEntity::class],version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationRoomDao
    abstract fun weatherCurrent(): WeatherCurrentDao
}