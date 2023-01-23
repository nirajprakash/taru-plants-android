package com.taru.domain.plant.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.taru.data.base.remote.ApiResult
import com.taru.data.local.db.AppDatabase
import com.taru.data.local.db.DatabaseConstants
import com.taru.data.local.db.cached.CachedRemoteKeyDao
import com.taru.data.local.db.cached.CachedRemoteKeyEntity
import com.taru.data.local.db.plant.LocalPlantSource
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.data.remote.plants.RemotePlantsConstants
import com.taru.data.remote.plants.RemotePlantsSource
import com.taru.data.remote.plants.toRoomEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Niraj on 22-01-2023.
 */
@OptIn(ExperimentalPagingApi::class)
class PlantsSearchMediator @Inject constructor(
    var q: String,
var remotePlantsSource: RemotePlantsSource,
var localPlantSource: LocalPlantSource,
var cachedRemoteKeyDao: CachedRemoteKeyDao,
    val db: AppDatabase
): RemoteMediator<Int, PlantSearchEntryEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlantSearchEntryEntity>
    ): MediatorResult {


        try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
//                    Timber.i("REFRESH")
                    if(getFirstRemoteKey(state) !=null)  MediatorResult.Success(endOfPaginationReached = false)
                    null
                }

                LoadType.PREPEND -> {
                  return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
//                    Timber.i("APPEND")

                    val remoteKey = getLastRemoteKey(state)
                    if (remoteKey?.nextKey == null)
                       return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKey
                }
            }

            if (loadKey != null) {
                if (loadKey.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page: Int = loadKey?.nextKey ?: RemotePlantsConstants.PAGE_FIRST
            val apiResponse = remotePlantsSource.plantsByQuery(q, page)

            if(apiResponse !is ApiResult.Success){
                // TODO error apiResponse.data
                if(apiResponse is ApiResult.Exception){
                    return MediatorResult.Error(apiResponse.throwable)
                }else if(apiResponse is ApiResult.Message){
                    return MediatorResult.Error(Throwable("Network mess: ${apiResponse.message}"))

                }
            }

            val remoteData = (apiResponse as ApiResult.Success).data

            val endOfPaginationReached = remoteData.data.size < RemotePlantsConstants.PAGE_SIZE

            db.withTransaction {
                val nextKey = page + 1

                cachedRemoteKeyDao.insert(
                    CachedRemoteKeyEntity(
                        nextKey = nextKey,
                        refId = 1,
                        refType = DatabaseConstants.Cached.REF_TYPE_PLANT,
                        q = q,
                        prevKey = null,

                        isEndReached = endOfPaginationReached
                    )
                )
                localPlantSource.addAll(remoteData.data.mapIndexed { index, plantsSearchEntryDto -> plantsSearchEntryDto.toRoomEntity(index, q) })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }
    private suspend fun getFirstRemoteKey(state: PagingState<Int, PlantSearchEntryEntity>) : CachedRemoteKeyEntity? {
        return  withContext(Dispatchers.IO){ cachedRemoteKeyDao.getKeyFirst(DatabaseConstants.Cached.REF_TYPE_PLANT, q).firstOrNull()
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, PlantSearchEntryEntity>) : CachedRemoteKeyEntity? {
        return withContext(Dispatchers.IO){
            state.pages
                .lastOrNull{it.data.isNotEmpty()}
                ?.data?.lastOrNull()
                ?.let { plantEntry -> cachedRemoteKeyDao.getKey(plantEntry.plantId, DatabaseConstants.Cached.REF_TYPE_PLANT, q).firstOrNull() }
        }
    }
}