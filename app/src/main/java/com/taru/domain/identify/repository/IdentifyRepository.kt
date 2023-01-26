package com.taru.domain.identify.repository

import android.net.Uri
import androidx.paging.PagingData
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.domain.base.result.DomainResult
import com.taru.domain.identify.model.ModelIdentified
import kotlinx.coroutines.flow.Flow

/**
 * Created by Niraj on 26-01-2023.
 */
interface IdentifyRepository {

    suspend fun identify(organ: String, uri: Uri): DomainResult<ModelIdentified>
    suspend fun isAllowed(): DomainResult<Boolean>
}