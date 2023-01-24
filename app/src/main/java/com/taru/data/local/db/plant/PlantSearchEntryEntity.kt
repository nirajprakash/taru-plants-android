package com.taru.data.local.db.plant

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.taru.ui.pages.nav.plants.recommended.ModelPlant

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

    companion object{
        var diffCallback: DiffUtil.ItemCallback<PlantSearchEntryEntity> =
            object : DiffUtil.ItemCallback<PlantSearchEntryEntity>() {

                override fun areItemsTheSame(
                    oldItem: PlantSearchEntryEntity,
                    newItem: PlantSearchEntryEntity
                ): Boolean {
                    return oldItem.id == newItem.id                }

                override fun areContentsTheSame(
                    oldItem: PlantSearchEntryEntity,
                    newItem: PlantSearchEntryEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}