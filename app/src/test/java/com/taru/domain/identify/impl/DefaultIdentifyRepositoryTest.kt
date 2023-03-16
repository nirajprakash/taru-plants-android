package com.taru.domain.identify.impl

import android.content.Context
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.taru.data.base.remote.ApiResult
import com.taru.data.local.source.LocalLogSource
import com.taru.data.remote.identify.RemoteIdentifySource
import com.taru.data.remote.identify.dto.PlantIdentifyDto
import dagger.hilt.android.qualifiers.ApplicationContext
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import me.shouheng.compress.Compress
import me.shouheng.compress.concrete
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/**
 * Created by Niraj on 16-03-2023.
 */
@RunWith(AndroidJUnit4::class)
class DefaultIdentifyRepositoryTest {


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var defaultIdentifyRepository: DefaultIdentifyRepository

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var remoteIdentifySource: RemoteIdentifySource

    @MockK
    lateinit var localLogSource: LocalLogSource



    @Before
    fun setUp() {


        MockKAnnotations.init(this)
        defaultIdentifyRepository = spyk(DefaultIdentifyRepository(context, remoteIdentifySource, localLogSource), recordPrivateCalls = true)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `identify`() = runTest{
//        mockkConstructor(Compress::class)
//        constructedWith
//        coEvery {  constructedWith<Compress>().concrete { any() }.get() } returns File("check")
//        val mock = spyk(defaultIdentifyRepository, recordPrivateCalls = true)
//        coEvery { defaultIdentifyRepository["compressFile"](allAny<Uri>()) } returns File("check")
//        val mock = spyk<DefaultIdentifyRepository>(recordPrivateCalls = true) {}
        coEvery { defaultIdentifyRepository.compressFile(any()) } returns File("check")

//        coEvery {  anyConstructed<Compress>().concrete { any() }.get() } returns File("check")
        coEvery { localLogSource.insert(any()) } returns 1
        coEvery { remoteIdentifySource.identify( any(), any()) } returns ApiResult.Success(
            PlantIdentifyDto("English", listOf())
        )

        var result = defaultIdentifyRepository.identify("ds", Uri.EMPTY)
        println("result:  $result")


//        mockkStatic(Compress::class)
//        Compress
//        every { Compress.Companion.with(any(), any()) } returns Compress()


    }
}