package com.taru.ui.pages.splash

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.taru.databinding.SplashFragmentBinding
import com.taru.databinding.WeatherFragmentBinding
import com.taru.domain.chart.mpchart.LineChartBinderHelper
import com.taru.ui.base.FragmentBase
import com.taru.ui.pages.weather.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

/**
 * Created by Niraj on 18-02-2023.
 */
@AndroidEntryPoint
class SplashFragment : FragmentBase(false) {

     private lateinit var vBinding: SplashFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding =
            SplashFragmentBinding.inflate(inflater, container, false).apply {
//                bViewModel = mViewModel
            }
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            delay(timeMillis = 500)
            navigateToHome()

        }
    }

    private fun navigateToHome() {
        var navCtrl = findNavController()
//            navCtrl.popBackStack()
//        navCtrl.navigate(LauncherFragmentDirections.)
        navCtrl.navigate(SplashFragmentDirections.navigateToHome())
    }

}