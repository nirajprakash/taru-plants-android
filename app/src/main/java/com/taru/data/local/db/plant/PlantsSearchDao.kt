package com.taru.data.local.db.plant

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taru.data.local.db.base.RoomDaoBase

/**
 * Created by Niraj on 22-01-2023.
 */
@Dao
interface PlantsSearchDao: RoomDaoBase<PlantSearchEntryEntity> {
    @Query("SELECT * FROM PlantsSearch WHERE q = :q ORDER BY `index` ASC")
    fun paginated(q: String): PagingSource<Int, PlantSearchEntryEntity>
    @Query("DELETE FROM PlantsSearch")
    fun deleteAll()

}