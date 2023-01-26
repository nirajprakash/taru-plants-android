package com.taru.data.remote.identify

import com.taru.BuildConfig
import com.taru.data.remote.identify.dto.PlantIdentifyDto
import com.taru.data.remote.plants.dto.PlantsSearchDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Niraj on 26-01-2023.
 */
interface ApiIdentify {
    @POST("identify/{project}")
    suspend fun identify(@Path("project") project: String = "all",
                    @Part("images") images: MultipartBody.Part,
                    @Part("organs") organ: String
    ): Response<PlantIdentifyDto>
}