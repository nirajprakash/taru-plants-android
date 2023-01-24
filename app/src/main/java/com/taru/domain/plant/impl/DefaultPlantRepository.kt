package com.taru.domain.plant.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.taru.data.local.db.AppDatabase
import com.taru.data.local.db.cached.CachedRemoteKeyDao
import com.taru.data.local.db.cached.CachedRemoteKeySource
import com.taru.data.local.db.plant.LocalPlantSource
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.data.remote.plants.RemotePlantsConstants
import com.taru.data.remote.plants.RemotePlantsSource
import com.taru.domain.base.result.DomainResult
import com.taru.domain.plant.repository.PlantRepository
import com.taru.domain.plant.search.PlantsSearchMediator
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Niraj on 23-01-2023.
 */
@Singleton
class DefaultPlantRepository @Inject constructor(
    private val remotePlantsSource: RemotePlantsSource,
    private val localPlantSource: LocalPlantSource,

    private var cachedRemoteKeySource: CachedRemoteKeySource,
    val db: AppDatabase
): PlantRepository {



    @OptIn(ExperimentalPagingApi::class)
    override  fun searchPaginated(q: String): Flow<PagingData<PlantSearchEntryEntity>> {
        val pagingSourceFactory =
            { localPlantSource.getPageSource(q) }
        return Pager(
            config = PagingConfig(RemotePlantsConstants.PAGE_SIZE, maxSize = 300),//  enablePlaceholders = true

            remoteMediator = PlantsSearchMediator(q, remotePlantsSource, localPlantSource, cachedRemoteKeySource, db),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun clearData(): DomainResult.Success<Unit> {
        localPlantSource.removeAll()
       withContext(Dispatchers.IO) { cachedRemoteKeySource.deleteAll() }
        return DomainResult.Success(Unit)
    }
}