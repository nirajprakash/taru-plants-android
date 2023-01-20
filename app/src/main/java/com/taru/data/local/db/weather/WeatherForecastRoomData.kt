package com.taru.data.local.db.weather

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Niraj on 20-01-2023.
 */
data class WeatherForecastRoomData(
    @Embedded
    val forecast: WeatherForecastRoomEntity,
    @Relation(parentColumn = "id", entityColumn = "forecastId")
    val entries: List<ForecastEntryEntity> = emptyList()
) {
}