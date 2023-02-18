package com.taru.domain.plant.search

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.taru.data.base.remote.ApiResult
import com.taru.data.local.db.AppDatabase
import com.taru.data.local.db.DatabaseConstants
import com.taru.data.local.db.cached.CachedRemoteKeyEntity
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.data.local.source.CachedRemoteKeySource
import com.taru.data.local.source.LocalPlantSource
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.data.remote.plants.RemotePlantsConstants
import com.taru.data.remote.plants.RemotePlantsSource
import com.taru.data.remote.plants.toRoomEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.Date
import javax.inject.Inject

/**
 * Created by Niraj on 22-01-2023.
 */

// https://stackoverflow.com/questions/66813622/android-paging-3-loadtype-append-returns-null-remote-keys
@OptIn(ExperimentalPagingApi::class)
class PlantsSearchFilterEdiblePartMediator @Inject constructor(
    var filterForEdible: Boolean,
    var q: String,
    var remotePlantsSource: RemotePlantsSource,
    var localPlantSource: LocalPlantSource,
    var cachedRemoteKeySource: CachedRemoteKeySource,
    val db: AppDatabase
) : RemoteMediator<Int, PlantSearchEntryEntity>() {
/*
    override suspend fun initialize(): InitializeAction {
//        return
        return InitializeAction.SKIP_INITIAL_REFRESH
    }*/

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlantSearchEntryEntity>
    ): MediatorResult {


        try {

            Log.d("PlantSearchMediator", "1 type: $loadType")
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("PlantSearchMediator", "1 refresh")

//                    Timber.i("REFRESH")
                    val cachedremotekey = getFirstRemoteKey(state)

                    Log.d("PlantSearchMediator", "load refresh: $cachedremotekey")
                    if (cachedremotekey != null) return MediatorResult.Success(
                        endOfPaginationReached = false
                    )
                    null
                }

                LoadType.PREPEND -> {
                    Log.d("PlantSearchMediator", "prepend")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
//                    Timber.i("APPEND")
                    Log.d("PlantSearchMediator", "APPEND, ${state.lastItemOrNull()}")

                    val remoteKey = getLastRemoteKey(state)

                    Log.d("PlantSearchMediator", "load append: $remoteKey")
                    if (remoteKey?.nextKey == null)
                        return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKey
                }
            }

            if (loadKey != null) {
                Log.d("PlantSearchMediator", "loadKey: $loadKey")
                if (loadKey.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }

            Log.d("PlantSearchMediator", "load: $loadKey")

            val filterType = if(filterForEdible) RemotePlantsConstants.Filter.EDIBLE_PART else RemotePlantsConstants.Filter.NOT_EDIBLE_PART

            val page: Int = loadKey?.nextKey ?: RemotePlantsConstants.PAGE_FIRST
            val apiResponse = remotePlantsSource.plantsByFilter(q, filterType, page)

            if (apiResponse !is ApiResult.Success) {
                // TODO error apiResponse.data
                if (apiResponse is ApiResult.Exception) {
                    apiResponse.throwable.printStackTrace()
                    return MediatorResult.Error(apiResponse.throwable)
                } else if (apiResponse is ApiResult.Message) {
                    return MediatorResult.Error(Throwable("Network mess: ${apiResponse.message}"))

                }
            }

            val remoteData = (apiResponse as ApiResult.Success).data

            val endOfPaginationReached = remoteData.data.size < RemotePlantsConstants.PAGE_SIZE

            db.withTransaction {
                val nextKey = page + 1


                localPlantSource.addAll(remoteData.data.mapIndexed { index, plantsSearchEntryDto ->
                    plantsSearchEntryDto.toRoomEntity(
                        page*RemotePlantsConstants.PAGE_SIZE + index,
                        "${filterForEdible}:${q}"
                    )
                })
                cachedRemoteKeySource.insert(
                    CachedRemoteKeyEntity(
                        nextKey = nextKey,
                        refId = remoteData.data.lastOrNull()?.id ?:-1,
                        refType = DatabaseConstants.Cached.REF_TYPE_PLANT_FILTER_EDIBLE_PART,
                        q =  "${filterForEdible}:${q}",
                        prevKey = null,

                        isEndReached = endOfPaginationReached
                    )
                )
                // TODO add image reference
                localPlantSource.addRecentSearch(PlantRecentSearchEntity(q= "${filterForEdible}:${q}",
                    dt = (Date().time/1000).toInt(),
                    refType = DatabaseConstants.Cached.REF_TYPE_PLANT_FILTER_EDIBLE_PART,
                    imageUrl = null))

            }

//            delay(200)


            Log.d("PlantSearchMediator", "withTransaction:${loadKey} ${page} ")

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, PlantSearchEntryEntity>): CachedRemoteKeyEntity? {

        return withContext(Dispatchers.IO) {
            Log.d("PlantsSearchMediator", "getFirstRemoteKey: $q")

            cachedRemoteKeySource.getKeyFirst(DatabaseConstants.Cached.REF_TYPE_PLANT_FILTER_EDIBLE_PART,  "${filterForEdible}:${q}").firstOrNull()
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, PlantSearchEntryEntity>): CachedRemoteKeyEntity? {
        return withContext(Dispatchers.IO) {
            Log.d("PlantsSearchMediator", "getLastRemoteKey: ${state.pages.lastOrNull()?.data?.size}")
            if(state.lastItemOrNull()==null){
                return@withContext cachedRemoteKeySource.getKeyFirst(refType =  DatabaseConstants.Cached.REF_TYPE_PLANT_FILTER_EDIBLE_PART, q =  "${filterForEdible}:${q}").lastOrNull()
            }

            state.pages
                .lastOrNull { it.data.isNotEmpty() }
                ?.data?.lastOrNull()
                ?.let { plantEntry ->

                    Log.d("PlantsSearchMediator", "getLastRemoteKey: $plantEntry")
                    cachedRemoteKeySource.getKey(
                        plantEntry.plantId,
                        DatabaseConstants.Cached.REF_TYPE_PLANT_FILTER_EDIBLE_PART,
                        "${filterForEdible}:${q}"
                    ).firstOrNull()
                }
        }
    }
}