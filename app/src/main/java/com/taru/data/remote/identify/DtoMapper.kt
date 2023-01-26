package com.taru.data.remote.identify

import android.net.Uri
import com.taru.data.local.db.weather.WeatherCurrentRoomEntity
import com.taru.data.local.db.weather.inner.WeatherAttrEntity
import com.taru.data.local.db.weather.inner.WeatherSubEntity
import com.taru.data.remote.identify.dto.PlantIdentifyDto
import com.taru.data.remote.weather.dto.WeatherCurrentDto
import com.taru.domain.identify.model.ModelIdentified
import com.taru.domain.weather.WeatherConstants

/**
 * Created by Niraj on 26-01-2023.
 */
fun PlantIdentifyDto.toDomainModel(uri: Uri): ModelIdentified {


    var keyworks = mutableListOf<String>()
    if(results.size>0){
       keyworks = results[0].species.commonNames.fold(keyworks) { acc: MutableList<String>, s: String ->
            acc.addAll(
                s.split(" ")
            )
            acc
        }

    }
    return ModelIdentified(
        keywords = keyworks,
        uriStr = uri.toString()
    )

}