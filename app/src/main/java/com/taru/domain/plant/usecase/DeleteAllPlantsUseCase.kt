package com.taru.domain.plant.usecase

import androidx.paging.PagingData
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.domain.base.result.DomainResult
import com.taru.domain.plant.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Niraj on 24-01-2023.
 */
class DeleteAllPlantsUseCase  @Inject constructor(private var plantRepository: PlantRepository)  {

    suspend operator fun invoke( ): DomainResult<Unit> = plantRepository.clearData()
}