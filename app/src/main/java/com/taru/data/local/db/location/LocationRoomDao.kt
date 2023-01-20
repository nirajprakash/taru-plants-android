package com.taru.data.local.db.location

import androidx.room.Dao
import androidx.room.Query
import com.taru.data.local.db.base.RoomDaoBase

/**
 * Created by Niraj on 18-01-2023.
 */
@Dao
interface LocationRoomDao: RoomDaoBase<LocationRoomEntity> {

    /**
     * Get by id.
     * @return {@link [LocationRoomEntity](com.taru.data.local.db.location.LocationRoomEntity)} from the table with a specific id.
     */
    @Query("SELECT * FROM Location WHERE id = :id")
    suspend fun getById(id: Int):LocationRoomEntity?

    /**
     * Get all data from the Data table.
     */
    @Query("SELECT * FROM Location")
    suspend fun getData(): List<LocationRoomEntity>

    @Query("SELECT * FROM Location ORDER BY ABS(lat - :latitude) + ABS(lon - :longitude) ASC LIMIT :limit")
    suspend fun findByDistance(latitude:Float,longitude:Float, limit: Int = 1): List<LocationRoomEntity>

/*
    @Query("SELECT * FROM Location ORDER BY ABS(lat - :latitude) + ABS(lon - :longitude) ASC, forecastUnix DESC LIMIT :limit")
    suspend fun findByDistanceForForecast(latitude:Float,longitude:Float, limit: Int = 1): List<LocationRoomEntity>

    @Query("SELECT * FROM Location ORDER BY ABS(lat - :latitude) + ABS(lon - :longitude) ASC, weatherUnix DESC LIMIT :limit")
    suspend fun findByDistanceForCurrent(latitude:Float,longitude:Float, limit: Int = 1): List<LocationRoomEntity>
*/

}