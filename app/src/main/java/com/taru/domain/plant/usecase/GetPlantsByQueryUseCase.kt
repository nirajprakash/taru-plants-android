package com.taru.domain.plant.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.domain.base.result.DomainResult
import com.taru.domain.plant.repository.PlantRepository
import com.taru.domain.weather.enitity.ModelWeather
import com.taru.domain.weather.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Niraj on 23-01-2023.
 */

internal class GetPlantsByQueryUseCase  @Inject constructor(private var plantRepository: PlantRepository)  {

    operator fun invoke(q:String ): Flow<PagingData<PlantSearchEntryEntity>> = plantRepository.searchPaginated(q)
}