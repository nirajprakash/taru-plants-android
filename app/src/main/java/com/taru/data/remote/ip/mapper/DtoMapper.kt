package com.taru.data.remote.ip.mapper

import com.taru.data.remote.ip.dto.IpDto

/**
 * Created by Niraj on 16-01-2023.
 */
fun IpDto.toLatLng(): Array<Float> {
    return arrayOf(lat, lon)
}