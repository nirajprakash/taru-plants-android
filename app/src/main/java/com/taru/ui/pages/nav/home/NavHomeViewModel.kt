package com.taru.ui.pages.nav.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.taru.data.local.db.weather.WeatherCurrentRoomEntity
import com.taru.domain.base.result.DomainResult
import com.taru.domain.weather.usecase.GetWeatherForecastUseCase
import com.taru.domain.weather.usecase.GetWeatherUseCase
import com.taru.ui.base.ViewModelBase
import com.taru.ui.nav.NavManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Niraj on 08-01-2023.
 */
@HiltViewModel
internal class NavHomeViewModel @Inject constructor(private val navManager: NavManager,
                                           private val getWeatherUseCase: GetWeatherUseCase,
                                                    private val getWeatherForecastUseCase: GetWeatherForecastUseCase): ViewModelBase(){

    val bCurrentWeather = MutableLiveData<WeatherCurrentRoomEntity>()
    init {
        getWeather()
    }
    private fun getWeather() {
        viewModelScope.launch {
            getWeatherUseCase().also {


                when(it) {
                    is DomainResult.Success -> {
                        Log.d("TAG", "initList: ${it.value}")
                        bCurrentWeather.postValue(it.value!!)
                        /*mCurrentList = it.value.items
                        _mEventAds.postValue(LiveDataEvent(it.value.items))*/
                    }

                    is DomainResult.Failure -> {
                        // _mEventAds.postValue(LiveDataEvent(it.value.items))

                        if(it.throwable!=null){
                            it.throwable.printStackTrace()
                        }
                        Log.d("AdListViewModel", "getList: failure ${it.throwable}")

                    }
                }

            }

            getWeatherForecastUseCase().also {


                when(it) {
                    is DomainResult.Success -> {
                        Log.d("TAG", "F: initList: ${it.value}")
                        /*mCurrentList = it.value.items
                        _mEventAds.postValue(LiveDataEvent(it.value.items))*/
                    }

                    is DomainResult.Failure -> {
                        // _mEventAds.postValue(LiveDataEvent(it.value.items))

                        if(it.throwable!=null){
                            it.throwable.printStackTrace()
                        }
                        Log.d("AdListViewModel", "F: getList: failure ${it.throwable}")

                    }
                }

            }

        }
    }

    fun navigateToScan(){
        navManager.navigate(NavHomeFragmentDirections.actionGlobalToScan())
    }

    fun navigateToWeather(){
        navManager.navigate(NavHomeFragmentDirections.actionGlobalToWeather())
    }

    fun navigateToSearch(){
        navManager.navigate(NavHomeFragmentDirections.actionToSearch(null))
    }
}