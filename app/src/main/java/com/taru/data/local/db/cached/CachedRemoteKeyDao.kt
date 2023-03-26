package com.taru.data.local.db.cached

import androidx.room.Dao
import androidx.room.Query
import com.taru.data.local.db.base.RoomDaoBase
import com.taru.data.local.db.weather.WeatherCurrentRoomEntity

/**
 * Created by Niraj on 22-01-2023.
 */
@Dao
interface CachedRemoteKeyDao: RoomDaoBase<CachedRemoteKeyEntity> {

    @Query("SELECT * FROM CachedRemoteKey WHERE refId=:refId AND refType=:refType AND q=:q LIMIT :limit")
    suspend fun getKey(refId:Int,refType:Int, q:String, limit: Int = 1): List<CachedRemoteKeyEntity>

    @Query("SELECT * FROM CachedRemoteKey WHERE  refType=:refType AND q=:q ORDER BY id ASC LIMIT :limit")
    suspend fun getKeyFirst(refType:Int, q:String, limit: Int = 1): List<CachedRemoteKeyEntity>
    @Query("DELETE FROM CachedRemoteKey")
    fun deleteAll()

    @Query("Select count(*) from CachedRemoteKey")
    fun getCount(): Int

}