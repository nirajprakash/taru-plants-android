package com.taru.tools.livedata

import com.google.common.truth.Truth
import io.mockk.verify
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Niraj on 27-03-2023.
 */
class LiveDataEventTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `LiveDataEvent value is not same when called again`() {
        var event = LiveDataEvent(12)
        var value = event.dataIfNotConsumed()
        Truth.assertThat(value).isEqualTo(12)
        var value2ndTime = event.dataIfNotConsumed()
        Truth.assertThat(value2ndTime).isEqualTo(null)


    }
}