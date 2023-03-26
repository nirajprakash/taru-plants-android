package com.taru.data.local.db.cached

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.taru.BuildConfig
import com.taru.data.local.db.AppDatabase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Niraj on 14-03-2023.
 */

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(
    RobolectricTestRunner::class)
class CachedRemoteKeyDaoTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var cachedRemoteKeyDao: CachedRemoteKeyDao

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        cachedRemoteKeyDao = appDatabase.cachedRemoteKey()

    }

    @After
    fun tearDown() {
        appDatabase.close()
    }



    @Test
    fun getKey() = runTest {
        cachedRemoteKeyDao.insert(
            CachedRemoteKeyEntity( refType =  2, refId = 1, q =  "sa", nextKey = 2, prevKey = null, isEndReached = false),
            CachedRemoteKeyEntity( refType =  2, refId = 2, q =  "sa", nextKey = 3, prevKey = null, isEndReached = false),
            CachedRemoteKeyEntity( refType =  2, refId = 3, q =  "sa", nextKey = null, prevKey = null, isEndReached = false),
            CachedRemoteKeyEntity( refType =  2, refId = 4, q =  "sa", nextKey = null, prevKey = null, isEndReached = true))

        // Then
        val value = cachedRemoteKeyDao.getKey(refType = 2, q = "sa", refId = 2)
        Truth.assertThat(value.size).isEqualTo(1)
        Truth.assertThat(value[0].id).isEqualTo(2)

    }


    @Test
    fun `test unique index replace old`() = runTest {
        cachedRemoteKeyDao.insert(
            CachedRemoteKeyEntity( refType =  2, refId = 2, q =  "sa", nextKey = 2, prevKey = null, isEndReached = false),
            CachedRemoteKeyEntity( refType =  2, refId = 2, q =  "sa", nextKey = 2, prevKey = null, isEndReached = true)
        )

        // Then
        val value = cachedRemoteKeyDao.getKey(refType = 2, q = "sa", refId = 2)
        val count = cachedRemoteKeyDao.getCount()

//        println("keys: $value")
//        println("keys: $count")
        Truth.assertThat(count).isEqualTo(1)
        /* id should of second*/
        Truth.assertThat(value[0].isEndReached).isEqualTo(true)
//        Truth.assertThat(value[0].id).isEqualTo(2)

    }

}