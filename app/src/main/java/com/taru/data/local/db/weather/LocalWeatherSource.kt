package com.taru.data.local.db.weather

import com.taru.data.base.local.LocalResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Niraj on 19-01-2023.
 */
@Singleton
class LocalWeatherSource @Inject constructor(
    private var weatherCurrentDao: WeatherCurrentDao
) {
    suspend fun  getLastCurrent(locationId: Int) = withContext(Dispatchers.IO) {


        var locations = weatherCurrentDao.findByLocationId(locationId)

        if(locations.isNotEmpty()){
                return@withContext LocalResult.Success(locations[0])

        }


        return@withContext LocalResult.Message(404, "Not found")

    }

    suspend fun add(weatherCurrent: WeatherCurrentRoomEntity) = withContext(Dispatchers.IO) {
        val id = weatherCurrentDao.insert(weatherCurrent)

        return@withContext LocalResult.Success(id)


//        return@withContext LocalResult.Message(404, "Not found")
    }
}