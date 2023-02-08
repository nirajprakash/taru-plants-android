package com.taru.data.local.source

import android.util.Log
import com.taru.data.base.local.LocalResult
import com.taru.data.local.db.weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Niraj on 19-01-2023.
 */
@Singleton
class LocalWeatherSource @Inject constructor(
    private var weatherCurrentDao: WeatherCurrentDao,

    private var weatherForecastDao: WeatherForecastDao,
    private var weatherForecastEntryDao: WeatherForecastEntryDao
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

    suspend fun  getLastForecast(locationId: Int) = withContext(Dispatchers.IO) {


        var locations = weatherForecastDao.findByLocationId(locationId)

        if(locations.isNotEmpty()){
            return@withContext LocalResult.Success(locations[0])
        }

        return@withContext LocalResult.Message(404, "Not found")

    }
    suspend fun  getForecastById(id: Int) = withContext(Dispatchers.IO) {


        var roomData = weatherForecastDao.byId(id)

        if(roomData!=null){
            return@withContext LocalResult.Success(roomData)
        }

        return@withContext LocalResult.Message(404, "Not found")

    }

    suspend fun addForecast(weatherForecast: WeatherForecastRoomEntity) = withContext(Dispatchers.IO) {
        val id = weatherForecastDao.insert(weatherForecast)
        return@withContext LocalResult.Success(id)
//        return@withContext LocalResult.Message(404, "Not found")
    }

    suspend fun addForecastEntries(weatherForecastEntries: List<ForecastEntryEntity>) = withContext(Dispatchers.IO) {
        val ids = weatherForecastEntryDao.insert(*weatherForecastEntries.toTypedArray())
        return@withContext LocalResult.Success(ids)
//        return@withContext LocalResult.Message(404, "Not found")
    }

    suspend fun removeForecastForLocation(locationId: Int): LocalResult<Int> {
        val affected = weatherForecastDao.deleteByLocationId(locationId)
        Log.d("LocalWeatherSource", "removeForecastForLocation: $affected")
        return LocalResult.Success(affected)
    }
    suspend fun removeCurrentForLocation(locationId: Int): LocalResult<Int> {
        val affected = weatherCurrentDao.deleteByLocationId(locationId)
        Log.d("LocalWeatherSource", "removeForLocation: $locationId $affected")
        return LocalResult.Success(affected)
    }

    suspend fun removeAll() = withContext(Dispatchers.IO) {
        weatherCurrentDao.deleteAll()
        weatherForecastDao.deleteAll()
    }

}