package com.taru.data.local.db.weather

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.taru.data.local.db.base.RoomDaoBase

/**
 * Created by Niraj on 20-01-2023.
 */
@Dao
interface WeatherForecastDao : RoomDaoBase<WeatherForecastRoomEntity> {

    @Query("SELECT * FROM WeatherForecast WHERE id = :id")
    suspend fun byId(id:Int): WeatherForecastRoomData?
    @Transaction
    @Query("SELECT * FROM WeatherForecast WHERE locationId=:locationId ORDER BY dt ASC LIMIT :limit")
    suspend fun findByLocationId(locationId:Int, limit: Int = 1): List<WeatherForecastRoomData>

    @Query("DELETE FROM WeatherForecast WHERE locationId=:locationId")
    suspend fun deleteByLocationId(locationId:Int): Int

    @Query("DELETE FROM WeatherForecast")
    fun deleteAll()


}