package com.taru.ui.pages.slider

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.taru.domain.weather.usecase.GetWeatherForecastUseCase
import com.taru.domain.weather.usecase.GetWeatherUseCase
import com.taru.ui.base.ViewModelBase
import com.taru.ui.pages.detail.PlantDetailFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Niraj on 13-02-2023.
 */
@HiltViewModel
class SliderViewModel @Inject constructor(

): ViewModelBase(){

    private var mImageList : List<String> = listOf()

    var bImages = MutableLiveData<List<ModelSliderImage>>()

    fun initArgs(args: SliderFragmentArgs) {
        mImageList = args.images.toList()

        viewModelScope.launch {
            bImages.postValue(mImageList.mapIndexed { index, s ->   ModelSliderImage(index, s) })
        }

    }

}