package com.taru.domain.plant.usecase

import com.taru.data.local.db.plant.PlantDetailRoomData
import com.taru.domain.base.result.DomainResult
import com.taru.domain.plant.repository.PlantRepository
import javax.inject.Inject

/**
 * Created by Niraj on 02-02-2023.
 */
class GetPlantDetailByIdUseCase @Inject constructor(private var plantRepository: PlantRepository)  {

    suspend operator fun invoke(plantId: Int): DomainResult<PlantDetailRoomData> = plantRepository.getPlantDetail(plantId)
}