package com.taru.data.local.db.cached

import androidx.room.Query
import com.taru.data.local.db.base.RoomDaoBase
import com.taru.data.local.db.weather.WeatherCurrentRoomEntity

/**
 * Created by Niraj on 22-01-2023.
 */
interface CachedRemoteKeyDao: RoomDaoBase<CachedRemoteKeyEntity> {
    @Query("SELECT * FROM CachedRemoteKey WHERE refId=:refId AND refType=:refType AND q=:q AND  LIMIT :limit")
    suspend fun getKey(refId:Int,refType:Int, q:String, limit: Int = 1): List<CachedRemoteKeyEntity>


}