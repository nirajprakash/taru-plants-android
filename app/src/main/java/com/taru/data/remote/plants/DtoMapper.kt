package com.taru.data.remote.plants

import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.data.remote.plants.dto.inner.PlantsSearchEntryDto

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
