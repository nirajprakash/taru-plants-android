package com.taru.domain.identify.impl

import android.content.Context
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.taru.data.base.remote.ApiResult
import com.taru.data.local.source.LocalLogSource
import com.taru.data.remote.identify.RemoteIdentifySource
import com.taru.data.remote.identify.dto.PlantIdentifyDto
import com.taru.data.remote.identify.toDomainModel
import com.taru.domain.base.result.DomainResult
import com.taru.domain.base.result.assertThat
import com.taru.domain.identify.IdentifyConstants
import com.taru.domain.identify.model.ModelIdentified
import dagger.hilt.android.qualifiers.ApplicationContext
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import me.shouheng.compress.Compress
import me.shouheng.compress.concrete

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
    fun `identify success`() = runTest{
//        mockkConstructor(Compress::class)
//        constructedWith
//        coEvery {  constructedWith<Compress>().concrete { any() }.get() } returns File("check")
//        val mock = spyk(defaultIdentifyRepository, recordPrivateCalls = true)
//        coEvery { defaultIdentifyRepository["compressFile"](allAny<Uri>()) } returns File("check")
//        val mock = spyk<DefaultIdentifyRepository>(recordPrivateCalls = true) {}
//        coEvery {  anyConstructed<Compress>().concrete { any() }.get() } returns File("check")

        coEvery { defaultIdentifyRepository.compressFile(any()) } returns File("check")


        coEvery { localLogSource.insert(any()) } returns 1
        coEvery { remoteIdentifySource.identify( any(), any()) } returns ApiResult.Success(
            PlantIdentifyDto("English", listOf())
        )

        val result = defaultIdentifyRepository.identify("leaf", Uri.parse("file://plant-image.jpg"))

        println("result : ${result}")
        val expectedResult = ModelIdentified(listOf(),"file://plant-image.jpg")
        assertThat(result).isEqualTo(DomainResult.Success(expectedResult))



//        mockkStatic(Compress::class)
//        Compress
//        every { Compress.Companion.with(any(), any()) } returns Compress()


    }

    @Test
    fun `isAllowed return false`() = runTest{

        coEvery { localLogSource.getCountForDay() } returns IdentifyConstants.ALLOWED_COUNT +1
        var result = defaultIdentifyRepository.isAllowed()
//        println("result:  $result")
//        assertThat(result).isEqualTo()
        assertThat(result).isEqual(false)

//        Truth.assertThat(result).isEqualTo(DomainResult.Failure)
//        Truth.assertThat(result).isEqualTo(DomainResult.Failure)
//
//        Truth.assertThat(result).isEqualTo(false)

    }

    @Test
    fun `isAllowed return true`() = runTest{

        coEvery { localLogSource.getCountForDay() } returns IdentifyConstants.ALLOWED_COUNT
        var result = defaultIdentifyRepository.isAllowed()
//        println("result:  $result")
//        assertThat(result).isEqualTo()
        assertThat(result).isEqual(true)

    }
}