package com.taru.domain.plant.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.domain.base.result.DomainResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Niraj on 22-01-2023.
 */
interface PlantRepository {
    fun searchPaginated(q: String): Flow<PagingData<PlantSearchEntryEntity>>
    //PagingData<PlantSearchEntryEntity>
    fun recentSearchPaginated(q: String?): Flow<PagingData<PlantRecentSearchEntity>>


    suspend fun addRecentSearches(search: List<PlantRecentSearchEntity>): DomainResult<List<Long>>
    suspend fun clearData(): DomainResult<Unit>

}