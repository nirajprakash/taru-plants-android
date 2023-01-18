package com.taru.data.remote.ip.dto

import com.squareup.moshi.JsonClass

/**
 * Created by Niraj on 16-01-2023.
 */
@JsonClass(generateAdapter = true)
data class IpDto(var query: String, var lat: Float, var lon: Float){

}
