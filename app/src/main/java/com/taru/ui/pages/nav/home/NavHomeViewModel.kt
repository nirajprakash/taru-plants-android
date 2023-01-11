package com.taru.ui.pages.nav.home

import com.taru.ui.base.ViewModelBase
import com.taru.ui.nav.NavManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Niraj on 08-01-2023.
 */
@HiltViewModel
class NavHomeViewModel @Inject constructor(private val navManager: NavManager,): ViewModelBase(){


    fun navigateToScan(){
        navManager.navigate(NavHomeFragmentDirections.actionGlobalToScan())
    }
}