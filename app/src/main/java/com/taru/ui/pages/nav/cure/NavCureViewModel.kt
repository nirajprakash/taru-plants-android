package com.taru.ui.pages.nav.cure

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.taru.R
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.tools.livedata.LiveDataEvent
import com.taru.ui.base.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Niraj on 08-01-2023.
 */
@HiltViewModel
class NavCureViewModel @Inject constructor(): ViewModelBase(){

    var guidance1 = ModelCureGuidance("Agri Farming - Source of Agriculture", "Farming, Horticulture, Crop Cultivation, Aquaculture, Livestock, Gardening, Subsidies, Loans",
    R.drawable.pic_farm, "https://www.agrifarming.in/")

    var guidance2 = ModelCureGuidance("Gardening Tips ", "Home, Indoors, Outdoors, Terrace, Balcony, Containers, Pots, Backyard.",
        R.drawable.pic_garden, "https://gardeningtips.in/")

    var guidance3 = ModelCureGuidance("Pests Database ", "Insecticide resistance for sustainable agriculture",
        R.drawable.pic_pests, "https://irac-online.org/")


    private val _mEventGuidance = MutableLiveData<LiveDataEvent<String>>()
    val mEventGuidance: LiveData<LiveDataEvent<String>> = _mEventGuidance

    var bAction = View.OnClickListener { view ->
        var tag = view.tag
        if(tag is String){
            _mEventGuidance.postValue(LiveDataEvent(tag))
        }
    }
}