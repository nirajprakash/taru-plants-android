package com.taru.di

import android.app.Application
import android.util.Log
import com.taru.App
import com.taru.BuildConfig
import com.taru.data.remote.ip.ApiIp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Niraj on 16-01-2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideCacheInterceptor() = Interceptor { chain ->
        var request = chain.request()
        //LOGW(TAG, "cache interceptor offline")
        if (!App.hasNetwork()) {
            // LOGW(TAG, "cache interceptor offline2")
            val cacheControl = CacheControl.Builder()
                .maxStale(7, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .cacheControl(cacheControl)
                .removeHeader("Authorization")
                .build()
        }

        chain.proceed(request)
    }

    @Provides
    fun provideCache(application: Application) = try {
        Cache(
            File(application.cacheDir, "http-cache"),
            10 * 1024 * 1024
        ) // 10 MB
    } catch (e: Exception) {
        null
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(cacheInterceptor: Interceptor,  cache: Cache?): OkHttpClient {
        //authErrorInterceptor: AuthErrorInterceptor,
        // authInterceptor: AuthInterceptor,
        val httpClient = OkHttpClient.Builder()
        // TODO getUnsafeOkHttpClient(httpClient)

     /*   httpClient.connectTimeout(55, TimeUnit.SECONDS)
        httpClient.readTimeout(55, TimeUnit.SECONDS)
        httpClient.writeTimeout(55, TimeUnit.SECONDS)*/

        // add your other interceptors …
        // add logging as last interceptor
//        httpClient.addInterceptor(authInterceptor)

        if (BuildConfig.DEBUG) {
            Log.d("retrofit", "provideOkHttpClient: ")
            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }


        httpClient.addInterceptor(cacheInterceptor)
        //httpClient.addNetworkInterceptor(provideCacheInterceptor());
        httpClient.cache(cache)
//        httpClient.addInterceptor(authErrorInterceptor)
        return httpClient.build()

    }


    @Singleton
    @Provides
    fun provideRetrofitIp(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://ip-api.com/")
        .client(okHttpClient)
//        .addCallAdapterFactory(retrofitAuthAdapterFactory)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun provideApiIp(retrofit: Retrofit): ApiIp = retrofit.create(ApiIp::class.java)


    /*
    * suspend fun getAdByName(path: String)= withContext(Dispatchers.IO) {
        var data: ModelAdTemplate? = null
        try {
            val jsonString = Utils.readJson(context, path)

            val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val jsonAdapter: JsonAdapter<ModelAdTemplate> = moshi.adapter(ModelAdTemplate::class.java)
            data = jsonAdapter.fromJson(jsonString)

        }catch (
            e: Throwable
        ){
            e.printStackTrace()
            return@withContext LocalResult.Exception(e)

        }

//        Log.d("LocalAdsSource", "getAds: ${data}")
        if(data!=null){
            return@withContext LocalResult.Success(data)
        }else {
            return@withContext LocalResult.Message(12, "Data Empty")

        }
    }
    *
    * */
}