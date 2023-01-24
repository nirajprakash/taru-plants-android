package com.taru.domain.plant.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.domain.base.result.DomainResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Niraj on 22-01-2023.
 */
interface PlantRepository {
    suspend fun searchPaginated(q: String): DomainResult<Flow<PagingData<PlantSearchEntryEntity>>>
    //PagingData<PlantSearchEntryEntity>

    suspend fun clearData(): DomainResult<Unit>
}