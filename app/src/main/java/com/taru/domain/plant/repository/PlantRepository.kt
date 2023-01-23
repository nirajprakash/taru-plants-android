package com.taru.domain.plant.repository

import androidx.paging.PagingData
import com.taru.data.local.db.plant.PlantSearchEntryEntity

/**
 * Created by Niraj on 22-01-2023.
 */
interface PlantRepository {
    suspend fun searchPaginated(q: String): PagingData<PlantSearchEntryEntity>
}