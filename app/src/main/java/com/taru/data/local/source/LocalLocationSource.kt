package com.taru.data.local.source

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
 * Created by Niraj on 18-01-2023.
 */
@Singleton
class LocalLocationSource @Inject constructor(
    private var locationRoomDao: LocationRoomDao
) {

    suspend fun getLastNearest(lat: Float, lon: Float) = withContext(Dispatchers.IO) {


        val locations = locationRoomDao.findByDistance(lat, lon)

            /*if(isForForecast){
            locationRoomDao.findByDistanceForForecast(lat, lon)
        }else{
            locationRoomDao.findByDistanceForCurrent(lat, lon)
        }*/

        if (locations.isNotEmpty()) {
            var locA = Location("A")
            locA.latitude = lat.toDouble()
            locA.longitude = lon.toDouble()
            var locB = Location("B")
            locB.latitude = locations[0].lat.toDouble()
            locB.longitude = locations[0].lon.toDouble()

            val distance = locA.distanceTo(locB)
            Log.d("LocalLocationSource", "getLastNearest: $distance")
            if (distance < 500) {

                return@withContext LocalResult.Success(locations[0])
               /* var date = (Date().time/1000).toInt()
                var period = if(isForForecast){
                    WeatherConstants.FORECAST_REFRESH_PERIOD
                }else {
                    WeatherConstants.CURRENT_REFRESH_PERIOD
                }
                var dt = if(isForForecast){
                    locations[0].forecastUnix
                }else {
                    locations[0].weatherUnix
                }

                if(dt!=null  && dt > date -period){
                    return@withContext LocalResult.Success(locations[0])
                }*/



            }
        }

        var id = locationRoomDao.insert(
            LocationRoomEntity(
                lat = lat,
                lon = lon,
//                dt = (Date().time / 1000).toInt()
            )
        )


        var location = locationRoomDao.getById(id.toInt())
        if (location != null) {
            return@withContext LocalResult.Success(location)
        }


//        Log.d("LocalAdsSource", "getAds: ${data}")
        /*if(data!=null){
            return@withContext LocalResult.Success(data)
        }else {*/
        return@withContext LocalResult.Message(404, "Not found")

//        }

    }

    suspend fun update(location: LocationRoomEntity) {

        var locations = locationRoomDao.update(location)

    }

}