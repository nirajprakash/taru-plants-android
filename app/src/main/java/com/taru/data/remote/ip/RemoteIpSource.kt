package com.taru.data.remote.ip

import com.taru.data.base.remote.handleApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Niraj on 16-01-2023.
 */

@Singleton
class RemoteIpSource @Inject constructor(
    var apiIp: ApiIp
){

    suspend fun  getIp() = withContext(Dispatchers.IO) {

        return@withContext handleApi { apiIp.getIp() }


    }
}