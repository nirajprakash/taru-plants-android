package com.taru.data.local.db.plant

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taru.data.local.db.base.RoomDaoBase

/**
 * Created by Niraj on 25-01-2023.
 */
@Dao
interface PlantRecentSearchDao : RoomDaoBase<PlantRecentSearchEntity> {

    @Query("SELECT * FROM PlantRecentSearch ORDER BY `dt` DESC")
    fun paginated(): PagingSource<Int, PlantRecentSearchEntity>

    @Query("SELECT * FROM PlantRecentSearch WHERE q = :q ORDER BY `dt` DESC")
    fun paginated(q: String): PagingSource<Int, PlantRecentSearchEntity>



}