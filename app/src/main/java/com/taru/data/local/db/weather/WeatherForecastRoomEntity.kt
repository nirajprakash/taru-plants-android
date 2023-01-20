package com.taru.data.local.db.weather

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.taru.data.local.db.location.LocationRoomEntity

/**
 * Created by Niraj on 20-01-2023.
 */
@Entity(tableName = "WeatherForecast",

    indices=[Index("locationId"), Index("dt")],
    foreignKeys =
[ForeignKey(
    onDelete = ForeignKey.CASCADE, entity = LocationRoomEntity::class, parentColumns = ["id"],
    childColumns = ["locationId"],
    )])
data class WeatherForecastRoomEntity(
    @PrimaryKey(autoGenerate = true)  val id:Int = 0,
    val count: Int,
    val dt: Int,
    val locationId: Int,
) {
}