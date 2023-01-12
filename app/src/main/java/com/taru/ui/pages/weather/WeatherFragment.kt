package com.taru.ui.pages.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.taru.R
import com.taru.databinding.WeatherFragmentBinding
import com.taru.ui.base.FragmentBase
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Niraj on 12-01-2023.
 */
@AndroidEntryPoint
class WeatherFragment : FragmentBase(true){

    private val mViewModel: WeatherViewModel by viewModels()
    private lateinit var vBinding: WeatherFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding =
            WeatherFragmentBinding.inflate(inflater, container, false).apply {
                bViewModel = mViewModel
            }
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vBinding.lifecycleOwner = this.viewLifecycleOwner

        lifecycleScope.launchWhenCreated {
            vBinding.weatherImage.load(R.drawable.pic_weather)
        }
    }
}