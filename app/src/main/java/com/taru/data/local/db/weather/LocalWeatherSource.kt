package com.taru.data.local.db.weather

import android.location.Location
import android.util.Log
import com.taru.data.base.local.LocalResult
import com.taru.data.local.db.location.LocationRoomDao
import com.taru.data.local.db.location.LocationRoomEntity
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
    suspend fun  getLast(locationId: Int) = withContext(Dispatchers.IO) {


        var locations = weatherCurrentDao.findByLocationId(locationId)

        if(locations.isNotEmpty()){
                return@withContext LocalResult.Success(locations[0])

        }


        return@withContext LocalResult.Message(404, "Not found")

    }
}