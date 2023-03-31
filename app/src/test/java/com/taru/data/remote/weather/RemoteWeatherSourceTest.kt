package com.taru.data.remote.weather

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.taru.data.remote.MockRetrofit
import com.taru.data.remote.weather.dto.WeatherCurrentDto
import com.taru.data.remote.weather.dto.inner.WeatherDto
import com.taru.data.remote.weather.dto.inner.WeatherLocationDto
import com.taru.data.remote.weather.dto.inner.WeatherMainDto
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection

/**
 * Created by Niraj on 12-03-2023.
 */
// https://github.com/sachin1kumar/Retrofit-Testing/blob/master/app/src/test/java/com/manager/retrofitesting/RequestViewModelTest.kt
//@RunWith(AndroidJUnit4::class)
class RemoteWeatherSourceTest {

    lateinit var apiWeather: ApiWeather

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        mockWebServer = MockWebServer()
        mockWebServer.start()
        var baseUrl = mockWebServer.url("/").toString()
//        println("MockWebServer url: ${baseUrl}")
        apiWeather = MockRetrofit(baseUrl).retrofit.create(ApiWeather::class.java)


//        apiWeather =

//        val apiInterfaceWeather: ApiWeather = getRetrofit().create(ApiInterface::class.java)


    }


    @Test
    fun getCurrent() =  runTest {
        // https://github.com/instipod/DuoUniversalKeycloakAuthenticator/issues/4

        val currentWeather =
            WeatherCurrentDto(1, WeatherLocationDto(72.1f,22.3f), listOf(WeatherDto(2, "Sunny")),
                WeatherMainDto(277.15f,10000,2,273.15f, 283.15f),
                12, (System.currentTimeMillis()/1000).toInt()
            )

        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<WeatherCurrentDto> = moshi.adapter(WeatherCurrentDto::class.java)//<WeatherCurrentDto>()

        val json: String = jsonAdapter.toJson(currentWeather)

         val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiWeather.getCurrent(22f, 72f)
        Truth.assertThat(actualResponse.code()).isEqualTo(HttpURLConnection.HTTP_OK)
        Truth.assertThat(actualResponse.body()).isEqualTo(currentWeather)
//        val result  = actualResponse.body()
//        println("result: $result")
    }

    @Test
    fun getForecast() = runTest{
    }

    @After
    fun tearDown()  {
        mockWebServer.shutdown()

    }

//    @Test
//    fun getApiWeather() {
//    }
//
//    @Test
//    fun setApiWeather() {
//    }
}