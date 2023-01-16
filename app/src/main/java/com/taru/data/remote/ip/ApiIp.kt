package com.taru.data.remote.ip

import com.taru.data.remote.ip.dto.IpDto
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Niraj on 16-01-2023.
 */
interface ApiIp {

    @GET("json")
    suspend fun getIp(
    ): Response<IpDto>
}