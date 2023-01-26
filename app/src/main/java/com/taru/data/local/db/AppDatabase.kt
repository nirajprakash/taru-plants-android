package com.taru.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taru.data.local.db.cached.CachedRemoteKeyDao
import com.taru.data.local.db.cached.CachedRemoteKeyEntity
import com.taru.data.local.db.location.LocationRoomDao
import com.taru.data.local.db.location.LocationRoomEntity
import com.taru.data.local.db.log.IdentifyLogRoomDao
import com.taru.data.local.db.log.IdentifyLogRoomEntity
import com.taru.data.local.db.plant.PlantRecentSearchDao
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.data.local.db.plant.PlantsSearchDao
import com.taru.data.local.db.weather.*

/**
 * Created by Niraj on 18-01-2023.
 */
@Database(
    entities = [LocationRoomEntity::class,
        WeatherCurrentRoomEntity::class,
        WeatherForecastRoomEntity::class,
        ForecastEntryEntity::class,
        PlantSearchEntryEntity::class,
        CachedRemoteKeyEntity::class,
        PlantRecentSearchEntity::class,
        IdentifyLogRoomEntity::class],
    version = 6,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationRoomDao
    abstract fun weatherCurrent(): WeatherCurrentDao
    abstract fun weatherForecast(): WeatherForecastDao
    abstract fun weatherForecastEntry(): WeatherForecastEntryDao
    abstract fun cachedRemoteKey(): CachedRemoteKeyDao
    abstract fun plantsSearchDao(): PlantsSearchDao
    abstract fun plantRecentSearchDao(): PlantRecentSearchDao
    abstract fun identifyLogDao(): IdentifyLogRoomDao

}