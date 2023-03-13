package com.taru.data.remote.weather

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Niraj on 12-03-2023.
 */
// https://www.bloco.io/blog/mocking-retrofit-api-responses-with-mockwebserver-hilt
// https://github.com/sachin1kumar/Retrofit-Testing/blob/master/app/src/test/java/com/manager/retrofitesting/RequestViewModelTest.kt
@RunWith(AndroidJUnit4::class)
class RemoteWeatherSourceTest {

    @MockK
    lateinit var apiWeather: ApiWeather

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        mockWebServer = MockWebServer()
        mockWebServer.start()

//        apiWeather =

//        val apiInterfaceWeather: ApiWeather = getRetrofit().create(ApiInterface::class.java)


    }

    @Test
    fun getCurrent() {
        val currentWeatherEntity = true
        Truth.assertThat(currentWeatherEntity).isEqualTo(true)

    }

    @Test
    fun getForecast() {
    }

    @After
    fun tearDown()  {

    }

//    @Test
//    fun getApiWeather() {
//    }
//
//    @Test
//    fun setApiWeather() {
//    }
}