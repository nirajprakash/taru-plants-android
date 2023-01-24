package com.taru.ui.labs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.taru.domain.plant.usecase.DeleteAllPlantsUseCase
import com.taru.tools.livedata.LiveDataEvent
import com.taru.ui.base.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Niraj on 20-11-2022.
 */
@HiltViewModel
class LabsMainViewModel @Inject constructor(
    val deleteAllPlantsUseCase: DeleteAllPlantsUseCase
): ViewModelBase() {


    private val _mEventNavigate = MutableLiveData<LiveDataEvent<Int>>()
    val mEventNavigate: MutableLiveData<LiveDataEvent<Int>> = _mEventNavigate




    fun navigateToDefault(){
//        Log.i("LabsMainViewModel", "navigateToDefault")
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.DEFAULT))
    }

    fun navigateToApp(){
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.NAV_APP))

    }

    fun clearPlants(){
        viewModelScope.launch {
            deleteAllPlantsUseCase.invoke()
        }

    }



}