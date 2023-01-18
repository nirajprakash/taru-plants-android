package com.taru.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taru.data.local.db.location.LocationRoomDao
import com.taru.data.local.db.location.LocationRoomEntity

/**
 * Created by Niraj on 18-01-2023.
 */
@Database(entities = [LocationRoomEntity::class],version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationRoomDao
}