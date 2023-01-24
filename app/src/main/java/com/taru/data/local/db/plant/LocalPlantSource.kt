package com.taru.data.local.db.plant

import androidx.paging.PagingSource
import com.taru.data.base.local.LocalResult
import com.taru.data.local.db.weather.WeatherCurrentDao
import com.taru.data.remote.plants.dto.PlantsSearchDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Niraj on 22-01-2023.
 */
@Singleton
class LocalPlantSource @Inject constructor(

    private var plantsSearchDao: PlantsSearchDao,
){

     fun  getPageSource(q: String): PagingSource<Int, PlantSearchEntryEntity> {


        val pageSource = plantsSearchDao.paginated(q)



        return pageSource

    }

    suspend fun addAll(entryEntityList: List<PlantSearchEntryEntity>)= withContext(Dispatchers.IO)  {
        val ids =  plantsSearchDao.insert(entryEntityList)
        return@withContext LocalResult.Success(ids)
    }

    suspend fun removeAll() = withContext(Dispatchers.IO) {
        plantsSearchDao.deleteAll()

    }
}