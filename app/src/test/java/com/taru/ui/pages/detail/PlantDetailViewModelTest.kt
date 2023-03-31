package com.taru.ui.pages.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.taru.data.local.db.plant.PlantDetailRoomData
import com.taru.data.local.db.plant.PlantEntity
import com.taru.domain.base.result.DomainResult
import com.taru.domain.plant.usecase.GetPlantDetailByIdUseCase
import com.taru.ui.nav.NavManager
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Niraj on 15-03-2023.
 */
@RunWith(AndroidJUnit4::class)
class PlantDetailViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var navManager: NavManager

    @MockK
    lateinit var getPlantDetailByIdUseCase: GetPlantDetailByIdUseCase

    private lateinit var plantDetailViewModel: PlantDetailViewModel

    @Before
    fun setUp() {

        MockKAnnotations.init(this)
        plantDetailViewModel = PlantDetailViewModel(navManager, getPlantDetailByIdUseCase)

    }

    @After
    fun tearDown() {
//        plantDetailViewModel.
    }

    @Test
    fun `check progress and detail state`() = runTest {
        val isProgressObserver: Observer<Boolean> = mockk(relaxUnitFun = true)
        plantDetailViewModel.bIsProgress.observeForever(isProgressObserver)

        val dataObserver: Observer<PlantDetailRoomData> = mockk(relaxUnitFun = true)
        plantDetailViewModel.bData.observeForever(dataObserver)

        val plantDetail = PlantDetailRoomData(
            detail = PlantEntity(
                12,
                speciesId = null,
                imageUrl = "",
                vegetable = false,
                commonName = null,
                familyName = null,
                genusName = null,
                scientificName = null,
                speciesName = null,
                edible = null,
                ediblePart = listOf(),
                natives = listOf(),
                lastQueriedDt = (System.currentTimeMillis() / 1000).toInt(),
                growth = null
            ),
            entries = listOf()
        )
        // When
        coEvery { getPlantDetailByIdUseCase.invoke(any()) } returns DomainResult.Success(plantDetail)
        plantDetailViewModel.initArgs(PlantDetailFragmentArgs(12))

        // Then
        val progressList = mutableListOf<Boolean>()
        verify { isProgressObserver.onChanged(capture(progressList)) }


        val dataList = mutableListOf<PlantDetailRoomData>()
        verify { dataObserver.onChanged(capture(dataList)) }

//        println("progressList: $progressList")
//        println("dataList: $dataList")

        Truth.assertThat(progressList.size).isEqualTo(3)
        Truth.assertThat(progressList[1]).isEqualTo(true)
        Truth.assertThat(dataList.size).isEqualTo(1)
        Truth.assertThat(dataList[0]).isEqualTo(plantDetail)
        plantDetailViewModel.bData.removeObserver(dataObserver)
        plantDetailViewModel.bIsProgress.removeObserver(isProgressObserver)
//
//        val loadingState = forecastViewStateSlots[0]
//        Truth.assertThat(loadingState.status).isEqualTo(Status.LOADING)

    }
}