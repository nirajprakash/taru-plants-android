package com.taru.domain.plant.usecase

import androidx.paging.PagingData
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.domain.plant.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Niraj on 25-01-2023.
 */
class AddPlantRecentSearchUseCase @Inject constructor(private var plantRepository: PlantRepository)  {

    suspend operator fun invoke(recentSearch:List<PlantRecentSearchEntity> ) = plantRepository.addRecentSearches(recentSearch)
}