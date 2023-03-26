package com.taru.data.remote.plants

import com.taru.data.base.remote.ApiResult
import com.taru.data.remote.plants.dto.PlantDetailResponseDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Created by Niraj on 19-03-2023.
 */
class RemotePlantsSourceTest {

    var apiPlants: ApiPlants = mockk()
    private val remotePlantsSource: RemotePlantsSource = RemotePlantsSource(apiPlants)

    @Before
    fun setUp() {


    }

    @After
    fun tearDown() {
    }

    @Test
    fun `plantDetailById server 404`() =  runTest{
        coEvery {
            apiPlants.byId(any())
        } returns Response.error(404, "{\n\"message\":\"not found\"\n}".toResponseBody(contentType = "application/json".toMediaTypeOrNull()))
        var result = remotePlantsSource.plantDetailById(12)

        if(result is ApiResult.Exception){
            result.throwable.printStackTrace()
            println("plantDetailById Exc: ${result.throwable}")
        }else if(result is ApiResult.Success){
            println("plantDetailById: $result")
        }else if(result is ApiResult.Message){
            println("plantDetailById Mess: ${result.message}")
        }

//        assertT


    }

    @Test
    fun `plantDetailById server success`() =  runTest{



//        assertT


    }

    @Test
    fun setApiPlants() {
    }
}