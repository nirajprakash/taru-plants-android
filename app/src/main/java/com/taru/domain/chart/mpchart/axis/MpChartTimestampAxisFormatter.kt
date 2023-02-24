package com.taru.domain.chart.mpchart.axis

import android.hardware.SensorManager
import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Niraj on 18-09-2022.
 */
class MpChartTimestampAxisFormatter() : ValueFormatter() {





    override fun getAxisLabel(value: Float, axis: AxisBase?): String {

       /* val LENGTH_SAMPLE: Int = mModelLineChart.getSampleLength()
        val labels = arrayOfNulls<String>(LENGTH_SAMPLE)
        val timePreiod: Float = mModelLineChart.getTimePeriod()
        for (i in labels.indices) {
            val `val` = i.toFloat() * timePreiod
            labels[i] = java.lang.Float.toString(`val`) + "s"
        }*/
//        var delay  = MAP_DELAY_TYPE_TO_DELAY.get(sensorDelay)
//        var totalDelay = value*delay;
        var calender = Calendar.getInstance()
        calender.add(Calendar.SECOND, value.toInt())
//        calender.time = Date(value.toLong()*1000)
        val sdf = SimpleDateFormat("EEE", Locale.getDefault())
        val d = calender.time
        Log.d("MpChartTimestampAxisFormatter", "$value: $d")
        val dayOfTheWeek: String = sdf.format(d)

        Log.d("MpChartTimestampAxisFormatter", "week: $dayOfTheWeek")
        return  dayOfTheWeek
//        return super.getAxisLabel(value, axis)
    }

}