package com.taru.ui.pages.scan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.taru.domain.base.result.DomainResult
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

    private val isAllowedUseCase: IsAllowedUseCase
) : ViewModelBase() {

    var bIsButtonEnabled = MutableLiveData(false)
    private val _mEventButtonAllowed = MutableLiveData<LiveDataEvent<Boolean>>()
    var mEventButtonAllowed: LiveData<LiveDataEvent<Boolean>> = _mEventButtonAllowed

    init {

    }

    fun enableButton() {
        isAllowed()
    }

    fun onClickScan() {
        Log.d("ScanViewModel", "onClickScan: ")
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


}