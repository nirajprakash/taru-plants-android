package com.taru.data.local.source

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.taru.data.base.local.LocalResult
import com.taru.data.local.assets.AssetsUtil
import com.taru.data.local.assets.entities.ModelCategoriesList
import com.taru.data.local.assets.entities.ModelCategory
import com.taru.data.local.db.DatabaseConstants
import com.taru.data.local.db.plant.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Niraj on 22-01-2023.
 */
@Singleton
class LocalPlantSource @Inject constructor(

    @ApplicationContext private var context: Context,
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
        if (q != null && q.isNotEmpty()) {
            Log.d("LocalPlantSource", "getPlantRecentSearchPageSource not null: $q")
            return plantRecentSearchDao.paginated("%$q%", DatabaseConstants.Cached.REF_TYPE_PLANT_SEARCH)
        }
        Log.d("LocalPlantSource", "getPlantRecentSearchPageSource null: $q")
        return plantRecentSearchDao.paginated(DatabaseConstants.Cached.REF_TYPE_PLANT_SEARCH) // return pageSource
    }

    suspend fun getPlantRecentSearchList() = withContext(Dispatchers.IO){
       return@withContext LocalResult.Success(plantRecentSearchDao.list(DatabaseConstants.DEFAULT_LIST_SIZE, DatabaseConstants.Cached.REF_TYPE_PLANT_SEARCH))
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
            var plant = plantDetail.detail
            plant.lastQueriedDt = (Calendar.getInstance().time.time/1000L).toInt()
            plantDetailDao.update(plantDetail.detail)
            return@withContext LocalResult.Success(plantDetail)
        }
        return@withContext LocalResult.Message(404, "Not found")
    }

    suspend fun getRecentPlantDetails() = withContext(Dispatchers.IO){
        val plantDetails = plantDetailDao.list(20)

        return@withContext LocalResult.Success(plantDetails)

    }

    suspend fun  getCategories() = withContext(Dispatchers.IO) {
        var data: ModelCategoriesList? = null
        try {
            val jsonString = AssetsUtil.readJson(context, "data/plants_categories.json")

            val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val jsonAdapter: JsonAdapter<ModelCategoriesList> = moshi.adapter(ModelCategoriesList::class.java)
            data = jsonAdapter.fromJson(jsonString)

        }catch (
            e: Throwable
        ){
            e.printStackTrace()
            return@withContext LocalResult.Exception(e)

        }

//        Log.d("LocalAdsSource", "getAds: ${data}")
        if(data!=null){
            return@withContext LocalResult.Success(data)
        }else {
            return@withContext LocalResult.Message(12, "Data Empty")

        }

    }

}