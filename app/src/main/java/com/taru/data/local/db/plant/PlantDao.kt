package com.taru.data.local.db.plant

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taru.data.local.db.base.RoomDaoBase

/**
 * Created by Niraj on 22-01-2023.
 */
@Dao
interface PlantDao: RoomDaoBase<PlantSearchEntryEntity> {
    @Query("SELECT * FROM Plants WHERE id = :plantId")
    suspend fun byPlantId(plantId: Int): PlantEntity



    @Query("DELETE FROM Plants")
    fun deleteAll()

}