package com.taru.ui.labs

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.domain.plant.usecase.AddPlantRecentSearchesUseCase
import com.taru.domain.plant.usecase.DeleteAllPlantsUseCase
import com.taru.domain.weather.usecase.DeleteAllWeatherUseCase
import com.taru.tools.livedata.LiveDataEvent
import com.taru.ui.base.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Created by Niraj on 20-11-2022.
 */
@HiltViewModel
class LabsMainViewModel @Inject constructor(
    private val deleteAllPlantsUseCase: DeleteAllPlantsUseCase,
    private val deleteAllWeatherUseCase: DeleteAllWeatherUseCase,
    private val addPlantRecentSearchesUseCase: AddPlantRecentSearchesUseCase
) : ViewModelBase() {

    private val _mEventNavigate = MutableLiveData<LiveDataEvent<Int>>()
    val mEventNavigate: MutableLiveData<LiveDataEvent<Int>> = _mEventNavigate


    fun navigateToDefault() {
//        Log.i("LabsMainViewModel", "navigateToDefault")
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.DEFAULT))
    }

    fun navigateToApp() {
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.NAV_APP))

    }

    fun clearWeather(){
        viewModelScope.launch {
            Log.d("LabsMainViewModel", "clearWeather: ")
            deleteAllWeatherUseCase.invoke()
        }
    }

    fun clearPlants() {
        viewModelScope.launch {
            deleteAllPlantsUseCase.invoke()
        }
    }

    fun addDummyRecentPlant() {
        viewModelScope.launch {
            var time = (Date().time / 1000).toInt()
            addPlantRecentSearchesUseCase.invoke(
                listOf(
                    PlantRecentSearchEntity(
                        q = "r",
                        dt = time - 2000,
                        imageUrl = "https://thumbs.dreamstime.com/b/small-plant-growing-garden-sunlight-137371294.jpg"
                    ),
                    PlantRecentSearchEntity(
                        q = "rq",
                        dt = time - 4000,
                        imageUrl = "https://thumbs.dreamstime.com/b/small-plant-growing-garden-sunlight-137371294.jpg"
                    ),
                    PlantRecentSearchEntity(
                        q = "q",
                        dt = time - 6000,
                        imageUrl = "https://thumbs.dreamstime.com/b/small-plant-growing-garden-sunlight-137371294.jpg"
                    ),
                    PlantRecentSearchEntity(
                        q = "as",
                        dt = time - 7000,
                        imageUrl = "https://thumbs.dreamstime.com/b/small-plant-growing-garden-sunlight-137371294.jpg"
                    ),
                    PlantRecentSearchEntity(
                        q = "asd",
                        dt = time - 8000,
                        imageUrl = "https://thumbs.dreamstime.com/b/small-plant-growing-garden-sunlight-137371294.jpg"
                    ),
                    PlantRecentSearchEntity(
                        q = "bs",
                        dt = time - 9000,
                        imageUrl = "https://thumbs.dreamstime.com/b/small-plant-growing-garden-sunlight-137371294.jpg"
                    ),
                    PlantRecentSearchEntity(
                        q = "bdd",
                        dt = time - 10000,
                        imageUrl = "https://thumbs.dreamstime.com/b/small-plant-growing-garden-sunlight-137371294.jpg"
                    ),

                    PlantRecentSearchEntity(
                        q = "ca",
                        dt = time - 11000,
                        imageUrl = "https://thumbs.dreamstime.com/b/small-plant-growing-garden-sunlight-137371294.jpg"
                    ),

                    PlantRecentSearchEntity(
                        q = "cv",
                        dt = time - 12000,
                        imageUrl = "https://thumbs.dreamstime.com/b/small-plant-growing-garden-sunlight-137371294.jpg"
                    ),

                    PlantRecentSearchEntity(
                        q = "cs",
                        dt = time - 13000,
                        imageUrl = "https://thumbs.dreamstime.com/b/small-plant-growing-garden-sunlight-137371294.jpg"
                    ),

                    PlantRecentSearchEntity(
                        q = "dq",
                        dt = time - 14000,
                        imageUrl = "https://thumbs.dreamstime.com/b/small-plant-growing-garden-sunlight-137371294.jpg"
                    ),
                )
            )
        }

    }


}