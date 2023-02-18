package com.taru.data.local.db.plant

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Niraj on 25-01-2023.
 */
@Entity(
    tableName = "PlantRecentSearch",
    indices = [Index(value = ["dt"]), Index(value = ["q"], unique = true)]
)
data class PlantRecentSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val q: String,
    val dt: Int,
    val refType: Int=0,
    val imageUrl: String?
) {

    companion object{
        var diffCallback: DiffUtil.ItemCallback<PlantRecentSearchEntity> =
            object : DiffUtil.ItemCallback<PlantRecentSearchEntity>() {

                override fun areItemsTheSame(
                    oldItem: PlantRecentSearchEntity,
                    newItem: PlantRecentSearchEntity
                ): Boolean {
                    return oldItem.id == newItem.id                }

                override fun areContentsTheSame(
                    oldItem: PlantRecentSearchEntity,
                    newItem: PlantRecentSearchEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}