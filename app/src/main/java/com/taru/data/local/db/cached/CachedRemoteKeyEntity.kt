package com.taru.data.local.db.cached

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Niraj on 22-01-2023.
 */

@Entity(tableName = "CachedRemoteKey", indices = [Index(value = ["q", "refId", "refType"], unique = true)])
data class CachedRemoteKeyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val refId: Int,
    val q:String = "",
    val refType:Int=0,
    val nextKey: Int?,
    val prevKey: Int?,

    val isEndReached: Boolean
) {
}