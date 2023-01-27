package com.taru.data.remote.identify

import com.taru.BuildConfig
import com.taru.data.remote.identify.dto.PlantIdentifyDto
import com.taru.data.remote.plants.dto.PlantsSearchDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Niraj on 26-01-2023.
 */
interface ApiIdentify {
    @Multipart
    @POST("identify/{project}")
    suspend fun identify(@Path("project") project: String = "all",
                         @Query("api-key") token: String = BuildConfig.PLANTNET_KEY,
                         @PartMap() partMap: MutableMap<String,RequestBody>,
                    @Part images: MultipartBody.Part
    ): Response<PlantIdentifyDto>
}