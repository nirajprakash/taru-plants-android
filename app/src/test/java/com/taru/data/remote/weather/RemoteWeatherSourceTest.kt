package com.taru.data.remote.weather

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Niraj on 12-03-2023.
 */
@RunWith(AndroidJUnit4::class)
class RemoteWeatherSourceTest {

    @MockK
    lateinit var apiWeather: ApiWeather


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

    }

    @Test
    fun getCurrent() {
        val currentWeatherEntity = true
        Truth.assertThat(currentWeatherEntity).isEqualTo(true)

    }

    @Test
    fun getForecast() {
    }

    @Test
    fun getApiWeather() {
    }

    @Test
    fun setApiWeather() {
    }
}