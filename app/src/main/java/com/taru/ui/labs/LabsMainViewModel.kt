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




    fun navigateToDefault(){
//        Log.i("LabsMainViewModel", "navigateToDefault")
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.DEFAULT))
    }

    fun navigateToApp(){
        _mEventNavigate.postValue(LiveDataEvent(LabsConstants.Navigation.NAV_APP))

    }



}