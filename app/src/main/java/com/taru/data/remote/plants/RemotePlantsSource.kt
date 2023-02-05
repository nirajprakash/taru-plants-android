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

        return@withContext handleApi { if(query!=null && query.trim().isNotEmpty()) apiPlants.byQ(query, page = page) else apiPlants.default( page = page) }


    }

    suspend fun  plantsByFilter(query: String?, filterType: String, page: Int ) = withContext(Dispatchers.IO) {

        val q: String = if(query!=null && query.trim().isNotEmpty()){
            query
        }else{
            "null"
        }

        return@withContext handleApi {   apiPlants.byFilter( filters = mutableMapOf(filterType to q), page = page) }


    }

    suspend fun plantDetailById(id: Int) = withContext(Dispatchers.IO){
        return@withContext handleApi {  apiPlants.byId(id) }


    }


}