package com.taru.data.local.db.plant

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.taru.data.local.db.plant.inner.PlantGrowthEntity
import com.taru.data.local.db.weather.inner.WeatherSubEntity
import com.taru.data.remote.plants.dto.inner.PlantSpeciesDto
import retrofit2.http.Field

/**
 * Created by Niraj on 30-01-2023.
 */
@Entity(tableName = "Plants")
data class PlantEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int ,
    var speciesId: Int?,
    val imageUrl: String,
    val vegetable: Boolean?,
    val commonName: String?,
    val familyName: String?,
    val genusName: String?,
    val scientificName: String?,
    val speciesName: String?,
    val edible: Boolean?,
    val ediblePart: List<String>,
    val natives: List<String>,


    @Embedded(prefix = "growth_")  val growth: PlantGrowthEntity?,
) {
}