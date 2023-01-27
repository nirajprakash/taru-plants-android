package com.taru.data.remote.identify.dto.inner

import com.squareup.moshi.JsonClass

/**
 * Created by Niraj on 26-01-2023.
 */
@JsonClass(generateAdapter = true)
data class PlantIdentifiedResult(val score: Float, val species: PlantIdentifiedResultSpecies ) {
}