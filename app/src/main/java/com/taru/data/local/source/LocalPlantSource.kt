package com.taru.data.local.source

import android.util.Log
import androidx.paging.PagingSource
import com.taru.data.base.local.LocalResult
import com.taru.data.local.db.plant.*
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
    private var plantDetailDao: PlantDetailDao,
    private var plantImageDao: PlantImageDao,
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

    suspend fun addRecentSearch(entity: PlantRecentSearchEntity) = withContext(Dispatchers.IO) {
        val id = plantRecentSearchDao.insert(entity)
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


    suspend fun addPlant(plantDetail: PlantEntity) = withContext(Dispatchers.IO) {
        val id = plantDetailDao.insert(plantDetail)
        return@withContext id
    }

    suspend fun addPlantImages(imageList: List<PlantImageEntity>) = withContext(Dispatchers.IO) {
        val ids = plantImageDao.insert(imageList)
        return@withContext ids
    }

    suspend fun  getPlantDetail(plantId: Int) = withContext(Dispatchers.IO) {
        val plantDetail = plantDetailDao.byId(plantId)
        if(plantDetail!=null){
            return@withContext LocalResult.Success(plantDetail)
        }
        return@withContext LocalResult.Message(404, "Not found")
    }

}