package com.taru.data.local.db.plant

import androidx.room.Embedded
import androidx.room.Relation
import com.taru.data.local.db.weather.ForecastEntryEntity
import com.taru.data.local.db.weather.WeatherForecastRoomEntity

/**
 * Created by Niraj on 30-01-2023.
 */
data class PlantDetailRoomData(
    @Embedded
    val detail: PlantEntity,
    @Relation(parentColumn = "id", entityColumn = "plantId")
    val entries: List<PlantImageEntity> = emptyList()
) {
}