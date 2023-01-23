package com.taru.data.remote.plants

import com.taru.data.base.remote.handleApi
import com.taru.data.remote.weather.ApiWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Niraj on 22-01-2023.
 */
class RemotePlantsSource @Inject constructor(
    var apiPlants: ApiPlants
){

    suspend fun  plantsByQuery(query: String?, page: Int ) = withContext(Dispatchers.IO) {

        return@withContext handleApi { apiPlants.byQ(query, page = page) }


    }


}