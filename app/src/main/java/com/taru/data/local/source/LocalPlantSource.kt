package com.taru.data.local.source

import android.util.Log
import androidx.paging.PagingSource
import com.taru.data.base.local.LocalResult
import com.taru.data.local.db.plant.PlantRecentSearchDao
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.data.local.db.plant.PlantsSearchDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Niraj on 22-01-2023.
 */
@Singleton
class LocalPlantSource @Inject constructor(

    private var plantRecentSearchDao: PlantRecentSearchDao,
    private var plantsSearchDao: PlantsSearchDao,
) {

    fun getPlantsSearchPageSource(q: String): PagingSource<Int, PlantSearchEntryEntity> {
        val pageSource = plantsSearchDao.paginated(q)
        return pageSource
    }

    suspend fun addAll(entryEntityList: List<PlantSearchEntryEntity>) =
        withContext(Dispatchers.IO) {
            val ids = plantsSearchDao.insert(entryEntityList)
            return@withContext LocalResult.Success(ids)
        }

    suspend fun removeAll() = withContext(Dispatchers.IO) {
        plantsSearchDao.deleteAll()

    }


    suspend fun addRecentSearch(entities: List<PlantRecentSearchEntity>) = withContext(Dispatchers.IO) {
        val id = plantRecentSearchDao.insert(entities)
        return@withContext LocalResult.Success(id)
    }

    fun getPlantRecentSearchPageSource(q: String?): PagingSource<Int, PlantRecentSearchEntity> {
        if (q != null) {
            Log.d("LocalPlantSource", "getPlantRecentSearchPageSource not null: $q")
            return plantRecentSearchDao.paginated("%$q%")
        }
        Log.d("LocalPlantSource", "getPlantRecentSearchPageSource null: $q")
        return plantRecentSearchDao.paginated() // return pageSource
    }

}