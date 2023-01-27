package com.taru.ui.pages.scan

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.taru.domain.base.result.DomainResult
import com.taru.domain.identify.usecase.IdentifyUseCase
import com.taru.domain.identify.usecase.IsAllowedUseCase
import com.taru.tools.livedata.LiveDataEvent
import com.taru.ui.base.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Niraj on 11-01-2023.
 */
@HiltViewModel
internal class ScanViewModel @Inject constructor(

    private val isAllowedUseCase: IsAllowedUseCase,
    private val identifyUseCase: IdentifyUseCase
//    private val
) : ViewModelBase() {

    var mUri: Uri? = null
    var bIsButtonEnabled = MutableLiveData(false)
    private val _mEventButtonAllowed = MutableLiveData<LiveDataEvent<Boolean>>()
    var mEventButtonAllowed: LiveData<LiveDataEvent<Boolean>> = _mEventButtonAllowed
    private val _mEventOnActionScan = MutableLiveData<LiveDataEvent<Boolean>>()
    var mEventOnActionScan: LiveData<LiveDataEvent<Boolean>> = _mEventOnActionScan

    init {

    }

    fun enableButton() {
        isAllowed()
    }

    fun onClickScan() {
        Log.d("ScanViewModel", "onClickScan: ")
        _mEventOnActionScan.postValue(LiveDataEvent(true))
    }

    private fun isAllowed() {
        viewModelScope.launch {
            var isAllowedResult = isAllowedUseCase.invoke()


            if (isAllowedResult is DomainResult.Success) {
                _mEventButtonAllowed.postValue(LiveDataEvent(isAllowedResult.value))
                bIsButtonEnabled.postValue(isAllowedResult.value)
            } else {
                _mEventButtonAllowed.postValue(LiveDataEvent(false))
                bIsButtonEnabled.postValue(false)
            }

        }
    }

    fun setUri(uri: Uri) {
        mUri = uri
    }

    fun identifyFor(organ: String) {


        viewModelScope.launch {
            if (mUri == null) return@launch
            identify(organ, mUri!!)
        }

    }

    private suspend fun identify(organ: String, uri: Uri) {
//        identifyUseCase.invoke("organ");
        var result = identifyUseCase.invoke(organ, uri)
        if (result is DomainResult.Success) {
            Log.d("ScanViewModel", "identify: ${result.value}")
        }else {
            if(result is DomainResult.Failure){
                result.throwable?.printStackTrace()
            }
        }

    }


}