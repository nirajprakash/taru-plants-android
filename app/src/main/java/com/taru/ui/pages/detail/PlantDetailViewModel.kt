package com.taru.ui.pages.detail

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.taru.data.local.db.plant.PlantDetailRoomData
import com.taru.domain.base.result.DomainResult
import com.taru.domain.plant.usecase.GetPlantDetailByIdUseCase
import com.taru.ui.base.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Niraj on 13-01-2023.
 */
@HiltViewModel
internal class PlantDetailViewModel @Inject constructor(var getPlantDetailByIdUseCase: GetPlantDetailByIdUseCase): ViewModelBase() {
    private var mPlantId: Int = -1


    val bIsProgress = MutableLiveData(false)
    val bData = MutableLiveData<PlantDetailRoomData>()
    fun initArgs(args: PlantDetailFragmentArgs) {
        mPlantId = args.plantId
        viewModelScope.launch {
            getDetail()
        }
    }
    //getPlantDetailByIdUseCase

    private suspend fun getDetail() {
//        identifyUseCase.invoke("organ");
        if(mPlantId == -1){
            return
        }
        bIsProgress.postValue(true)
        var result = getPlantDetailByIdUseCase.invoke(mPlantId)
        if (result is DomainResult.Success) {
            result.value.let {
                bData.postValue(it)
            }
            Log.d("PlantDetailViewModel", "details: ${result.value}")
        }else {
            if(result is DomainResult.Failure){
                result.throwable?.printStackTrace()
            }
        }

    }

}