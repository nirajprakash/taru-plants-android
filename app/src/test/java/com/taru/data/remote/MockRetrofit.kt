package com.taru.data.remote

import com.taru.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Niraj on 13-03-2023.
 */
object MockRetrofit {
//    val mChannelSize: Int by lazy { 23 }

    val retrofitWeather: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_KEY)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


}