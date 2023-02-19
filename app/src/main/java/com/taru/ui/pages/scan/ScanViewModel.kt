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
import com.taru.ui.nav.NavManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Niraj on 11-01-2023.
 */
@HiltViewModel
internal class ScanViewModel @Inject constructor(

    private val navManager: NavManager,
    private val isAllowedUseCase: IsAllowedUseCase,
    private val identifyUseCase: IdentifyUseCase
//    private val
) : ViewModelBase() {

    var mUri: Uri? = null

    var bIsProgress = MutableLiveData(false)
    var bIsButtonEnabled = MutableLiveData(false)
    var mIsButtonActive = false
    private val _mEventButtonAllowed = MutableLiveData<LiveDataEvent<Boolean>>()
    var mEventButtonAllowed: LiveData<LiveDataEvent<Boolean>> = _mEventButtonAllowed
    private val _mEventOnActionScan = MutableLiveData<LiveDataEvent<Boolean>>()
    var mEventOnActionScan: LiveData<LiveDataEvent<Boolean>> = _mEventOnActionScan

    var bScanMessage = MutableLiveData<String>()
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
            bIsProgress.postValue(true)
            if(mIsButtonActive){
                bIsButtonEnabled.postValue(false)
            }
            var isAllowedResult = isAllowedUseCase.invoke()


            if (isAllowedResult is DomainResult.Success) {
                _mEventButtonAllowed.postValue(LiveDataEvent(isAllowedResult.value))
                bIsButtonEnabled.postValue(isAllowedResult.value)
                mIsButtonActive =  isAllowedResult.value
            } else {
                _mEventButtonAllowed.postValue(LiveDataEvent(false))
                bIsButtonEnabled.postValue(false)
            }

            if(mIsButtonActive){
                bIsButtonEnabled.postValue(true)
            }
            bIsProgress.postValue(false)
        }
    }

    fun setUri(uri: Uri) {
        mUri = uri
    }

    fun identifyFor(organ: String) {


        viewModelScope.launch {
            if (mUri == null) return@launch
//            navigateToResult()
            identify(organ, mUri!!)
        }

    }

    private suspend fun identify(organ: String, uri: Uri) {
//        identifyUseCase.invoke("organ");

        bIsProgress.postValue(true)

        if(mIsButtonActive){
            bIsButtonEnabled.postValue(false)
        }
        var result = identifyUseCase.invoke(organ, uri)
        if (result is DomainResult.Success) {
            var message = result.value.keywords.joinToString(separator = ", ")
            bScanMessage.postValue(message)
            navigateToResult(result.value.keywords.toTypedArray())

            Log.d("ScanViewModel", "identify: ${result.value}")
        }else {
            if(result is DomainResult.Failure){

                result.throwable?.printStackTrace()
            }
        }

        if(mIsButtonActive){
            bIsButtonEnabled.postValue(true)
        }
        bIsProgress.postValue(false)
    }

    private fun navigateToResult(keywords: Array<String>){
        if(mUri == null)return
        navManager.navigate(ScanFragmentDirections.navigateToResult(mUri!!.toString(), keywords))
    }


}