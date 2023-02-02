package com.taru.data.remote.plants.dto

import com.squareup.moshi.JsonClass
import com.taru.data.local.db.plant.PlantImageEntity
import com.taru.data.remote.plants.dto.inner.*
import retrofit2.http.Field

/**
 * Created by Niraj on 30-01-2023.
 */

@JsonClass(generateAdapter = true)
data class PlantDetailDto(var id: Int,
                          @Field(value= "main_species_id") var mainSpeciesId: Int,
                          @Field(value = "image_url" ) val imageUrl: String,
                          val vegetable: Boolean?,
                          @Field(value = "common_name" ) val commonName: String?,
                          @Field(value = "scientific_name" ) val scientificName: String?,
                          val family: PlantFamilyDto,
                          val genus: PlantGenusDto,
                          @Field(value = "main_species") val mainSpecies: PlantSpeciesDto
                          ) {

}