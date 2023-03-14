package com.taru.data.remote

import com.taru.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Niraj on 13-03-2023.
 */
class MockRetrofit(var baseUrl: String) {
//    val mChannelSize: Int by lazy { 23 }



    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

//    fun testRetrofit(baseUrl: String): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
////            .create(TestApis::class.java)
//    }


}