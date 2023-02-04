package com.taru.ui.pages.nav.plants

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.domain.base.result.DomainResult
import com.taru.domain.plant.usecase.GetPlantRecentSearchListUseCase
import com.taru.tools.livedata.LiveDataEvent
import com.taru.ui.base.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Niraj on 08-01-2023.
 */
@HiltViewModel
internal class NavPlantsViewModel @Inject constructor(var recentSearchListUseCase: GetPlantRecentSearchListUseCase): ViewModelBase(){

    private val _mEventRecentSearchList = MutableLiveData<LiveDataEvent<List<PlantRecentSearchEntity>>>()
    val mEventRecentSearchList: LiveData<LiveDataEvent<List<PlantRecentSearchEntity>>> = _mEventRecentSearchList

    val bIsProgress = MutableLiveData(false)
    /*init {
        initList()
    }*/

    fun initList(){
        viewModelScope.launch {
            getRecent()
        }
    }

    private suspend fun getRecent() {

        bIsProgress.postValue(true)
        var result = recentSearchListUseCase.invoke()
        if (result is DomainResult.Success) {
            result.value.let {
                _mEventRecentSearchList.postValue(LiveDataEvent(it))
            }
//            Log.d("PlantDetailViewModel", "details: ${result.value}")
        }else {
            if(result is DomainResult.Failure){
                result.throwable?.printStackTrace()
            }
        }

    }
}