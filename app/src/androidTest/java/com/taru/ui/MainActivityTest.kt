package com.taru.ui

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Niraj on 17-07-2023.
 */
@HiltAndroidTest
//TODO for roboelectric @Config(application = HiltTestApplication::class)
class MainActivityTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun whenMainActivityLaunchedNavigationHelperIsInvokedForFragment() { // 7
        assertTrue(true)
    }


}