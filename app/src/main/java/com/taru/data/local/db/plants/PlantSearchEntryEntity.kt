package com.taru.data.local.db.plants

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Niraj on 22-01-2023.
 */
@Entity(tableName = "PlantsSearch", indices = [Index(value = ["index", "q"], unique = true)])
data class PlantSearchEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val plantId: Int,
    val q:String,
    val index:Int,
    val family: String,
    val slug: String,
    val commonName: String,
    val scientificName: String,
    val imageUrl: String?
) {
}