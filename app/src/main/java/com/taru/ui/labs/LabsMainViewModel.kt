package com.taru.ui.labs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.taru.tools.livedata.LiveDataEvent
import com.taru.ui.base.ViewModelBase

/**
 * Created by Niraj on 20-11-2022.
 */
class LabsMainViewModel: ViewModelBase() {


    private val _mEventNavigate = MutableLiveData<LiveDataEvent<Int>>()
    val mEventNavigate: MutableLiveData<LiveDataEvent<Int>> = _mEventNavigate


    fun navigateToSample1(){
//        Log.i("LabsMainViewModel", "navigateToSample1")
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.SAMPLE_1))
    }

    fun navigateTo3d(){
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.THREE_D))
    }

    fun navigateTo3dSense(){
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.THREE_D_SENSE))

    }
    fun navigateTo3dSense2(){
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.THREE_D_SENSE2))

    }

    fun navigateTo3dModel(){
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.THREE_D_MODEL))

    }

    fun navigateToDefault(){
//        Log.i("LabsMainViewModel", "navigateToDefault")
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.DEFAULT))
    }

    fun navigateToJson(){
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.JSON))
    }

    fun navigateTemplate(){
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.TEMPLATE))

    }
    fun navigateToLauncher(){
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.LAUNCHER))

    }
    fun navigateToAdListFragment(){
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.AD_LIST))

    }
    fun navigateToInfoFragment(){
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.INFO))

    }

}