package com.taru.domain.plant.usecase

import com.taru.data.local.db.plant.PlantEntity
import com.taru.domain.base.result.DomainResult
import com.taru.domain.plant.repository.PlantRepository
import javax.inject.Inject

/**
 * Created by Niraj on 14-02-2023.
 */
class GetPlantDetailsUseCase @Inject constructor(private var plantRepository: PlantRepository)  {

    suspend operator fun invoke(): DomainResult<List<PlantEntity>> = plantRepository.getRecentPlantList()
}