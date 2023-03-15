package com.taru.ui.pages.detail

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.taru.data.local.db.plant.PlantDetailRoomData
import com.taru.domain.base.result.DomainResult
import com.taru.domain.plant.usecase.GetPlantDetailByIdUseCase
import com.taru.ui.base.ViewModelBase
import com.taru.ui.nav.NavManager
import com.taru.ui.pages.scan.ScanFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Niraj on 13-01-2023.
 */
@HiltViewModel
internal class PlantDetailViewModel @Inject constructor(

    private val navManager: NavManager,
    var getPlantDetailByIdUseCase: GetPlantDetailByIdUseCase): ViewModelBase() {
    private lateinit var mPlantDetail: PlantDetailRoomData
    private var mPlantId: Int = -1

    var bKeywords = MutableLiveData<List<String>>()

    val bIsProgress = MutableLiveData(false)
    val bData = MutableLiveData<PlantDetailRoomData>()


    fun initArgs(args: PlantDetailFragmentArgs) {
        mPlantId = args.plantId
        viewModelScope.launch {
            bIsProgress.postValue(true)
            getDetail()
            bIsProgress.postValue(false)
        }
    }

    var bActionGallery = View.OnClickListener { view ->
        navigateToResult(mPlantDetail.entries.map{it.imageUrl})
//            _mEventOnActionGallery.postValue(LiveDataEvent(mPlantDetail.entries.map{it.imageUrl}))
    }
    //getPlantDetailByIdUseCase

    private suspend fun getDetail() {
//        identifyUseCase.invoke("organ");
        if(mPlantId == -1){
            return
        }
        var result = getPlantDetailByIdUseCase.invoke(mPlantId)
        if (result is DomainResult.Success) {
            result.value.let {
                bData.postValue(it)
                bKeywords.postValue(it.detail.natives.take(7))
                mPlantDetail = it
            }
            Log.d("PlantDetailViewModel", "details: ${result.value}")
        }else {
            if(result is DomainResult.Failure){
                result.throwable?.printStackTrace()
            }
        }

    }
    private fun navigateToResult(images: List<String>) {
        if(images.isEmpty())return
        navManager.navigate(PlantDetailFragmentDirections.navigateToSlider(images.toTypedArray()))
    }

}