package com.taru.data.local.db.log

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Niraj on 26-01-2023.
 */
@Entity(tableName = "IdentifyLog", indices = [Index(value = ["dt"], unique = true)])
data class IdentifyLogRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val dt: Int
) {
}