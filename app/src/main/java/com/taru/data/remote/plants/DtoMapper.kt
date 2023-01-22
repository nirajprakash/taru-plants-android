package com.taru.data.remote.plants

import com.taru.data.local.db.plants.PlantSearchEntryEntity
import com.taru.data.local.db.weather.WeatherCurrentRoomEntity
import com.taru.data.local.db.weather.inner.WeatherAttrEntity
import com.taru.data.local.db.weather.inner.WeatherSubEntity
import com.taru.data.remote.plants.dto.inner.PlantsSearchEntryDto
import com.taru.domain.weather.WeatherConstants

/**
 * Created by Niraj on 22-01-2023.
 */
fun PlantsSearchEntryDto.toRoomEntity(position: Int, q: String): PlantSearchEntryEntity {

//    val first = weather.firstOrNull()
     return PlantSearchEntryEntity(
     q=q,
        index = position,
        commonName = commonName,
        family = family,
        imageUrl = imageUrl,
        plantId = id,
        slug = slug,
        scientificName = scientificName
    )

}
