package com.taru.data.local.db.weather

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taru.data.local.db.base.RoomDaoBase
import com.taru.data.local.db.location.LocationRoomEntity

/**
 * Created by Niraj on 19-01-2023.
 */
@Dao
interface WeatherCurrentDao: RoomDaoBase<WeatherCurrentRoomEntity> {

    @Query("DELETE FROM WeatherCurrent WHERE locationId=:locationId")
    suspend fun deleteByLocationId(locationId:Int): Int


    @Query("SELECT * FROM WeatherCurrent WHERE locationId=:locationId ORDER BY dt ASC LIMIT :limit")
    suspend fun findByLocationId(locationId:Int, limit: Int = 1): List<WeatherCurrentRoomEntity>

    @Query("DELETE FROM WeatherCurrent")
    fun deleteAll()
}