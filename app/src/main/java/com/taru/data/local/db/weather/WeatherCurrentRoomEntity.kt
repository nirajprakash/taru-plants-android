package com.taru.data.local.db.weather

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import com.taru.data.local.db.location.LocationRoomEntity
import com.taru.data.local.db.weather.inner.WeatherAttrEntity
import com.taru.data.local.db.weather.inner.WeatherSubEntity

/**
 * Created by Niraj on 19-01-2023.
 */

@Entity(tableName = "WeatherCurrent",
    indices=[Index("locationId"), Index("dt")],
    foreignKeys =
    [ForeignKey(
        onDelete = CASCADE, entity = LocationRoomEntity::class, parentColumns = ["id"],
        childColumns = ["locationId"]
    )]
)
data class WeatherCurrentRoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val locationId: Int,
    val visibility: Int,
    val cityId: Int,
    val dt: Int,
    @Embedded(prefix = "weather_")  val weather: WeatherSubEntity?,
    @Embedded(prefix = "attr_") val attrs: WeatherAttrEntity
) {
}