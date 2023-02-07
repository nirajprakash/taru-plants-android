package com.taru.ui.pages.weather

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.google.android.material.color.MaterialColors
import com.taru.R
import com.taru.data.local.db.weather.ForecastEntryEntity
import com.taru.databinding.WeatherFragmentBinding
import com.taru.domain.chart.mpchart.axis.MpChartTimestampAxisFormatter
import com.taru.tools.livedata.LiveDataObserver
import com.taru.ui.base.FragmentBase
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Niraj on 12-01-2023.
 */
@AndroidEntryPoint
class WeatherFragment : FragmentBase(true) {

    private var colorSurface: Int = Color.WHITE
    private var colorOnSurface: Int = Color.BLACK
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
            colors()
            setupChart()


            mViewModel.initData()

        }
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        mViewModel.mEventForecastEntries.observe(viewLifecycleOwner, LiveDataObserver {
            bindToChart(it)
        })
    }

    private fun setupChart() {

        var vChart = vBinding.weatherFragmentForecastChart
        vChart.apply {
            //Set shadow
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//            renderer.paintRender.setShadowLayer(3F, 5F, 3F, Color.Gray.toArgb());
//            setViewPortOffsets(0f, 0f, 0f, 0f);


            applyAxis(this, )
            legend.isEnabled = true
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.setDrawInside(false)
            legend.textColor = colorOnSurface

            setBackgroundColor(colorSurface)
            setDrawGridBackground(false)
            setDrawBorders(false)
//            setDrawMarkers(true)


            description.isEnabled = false
            axisLeft.setDrawLabels(true)
//           axisRight.setDrawLabels(false)
            axisLeft.setDrawGridLines(true)
        }


    }

    private fun colors() {
        colorOnSurface = MaterialColors.getColor(
            requireContext(),
            com.google.android.material.R.attr.colorOnSurface,
            ContextCompat.getColor(requireContext(), R.color.color_back_54)
        )
        colorSurface = MaterialColors.getColor(
            requireContext(),
            com.google.android.material.R.attr.colorOnSurface,
            ContextCompat.getColor(requireContext(), R.color.color_back_54)
        )
    }

    private fun bindToChart(entities: List<ForecastEntryEntity>) {
        //TODO("Not yet implemented")
    }

    private fun applyAxis(lineChart: LineChart) {
//        ContextCompat.getColorStateList(requireContext(), R.color.colorOnSurface)


        lineChart.apply {
            //Formatting

            xAxis.valueFormatter = MpChartTimestampAxisFormatter()
            xAxis.granularity = 1f
            xAxis.setDrawAxisLine(true)
            xAxis.setDrawGridLines(false)
            xAxis.setDrawLabels(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.textColor = colorOnSurface

            axisRight.setDrawZeroLine(false)
            axisRight.isEnabled = false
            axisRight.textColor = colorOnSurface

            axisLeft.setDrawZeroLine(false)
            axisLeft.isEnabled = true
            axisLeft.setDrawAxisLine(true)
//           axisRight.setDrawAxisLine(false)
            axisLeft.setDrawLabels(false)
//           axisRight.setDrawLabels(false)
            axisLeft.setDrawGridLines(false)
//           axisRight.setDrawGridLines(false)
//            axisLeft.axisMinimum = 0f
            axisLeft.textColor = colorOnSurface
        }

    }
}