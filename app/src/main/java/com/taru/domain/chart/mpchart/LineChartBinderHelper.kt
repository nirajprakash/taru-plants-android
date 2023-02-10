package com.taru.domain.chart.mpchart

import android.hardware.Sensor
import android.util.Log
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.taru.domain.chart.mpchart.entities.LineChartDataSetConfig
import java.util.Calendar

/**
 * Created by Niraj on 07-02-2023.
 */
class LineChartBinderHelper {

    fun update(chart: LineChart,   dts: List<Int>, values: List<FloatArray>,) : Boolean {
        if (values.size == 0) return false;
        var lineData: LineData? = chart.data

        if(lineData==null){
//            Log.v("MpChartViewUpdater", "update no line data ")
            return false
        }
        chart.lineData.dataSets

        for (i in  chart.lineData.dataSets.indices) {
            lineData = updateDataSet(i, dts, values, lineData!!)

        }


        notifyDataChange(chart)

        //

        /*for (dataSet in datasets){
        }*/

        return true

    }

    private fun updateDataSet(
        index: Int, dts: List<Int>, values: List<FloatArray>,
        pLineData: LineData
    ): LineData {
        var chartLineData = pLineData

        if (index >= pLineData.dataSets.size) return pLineData;

        var dataset = pLineData.getDataSetByIndex(index)
//        modelDataSet

      dataset.clear()
//        val label: String = config.label
//        val entries: MutableList<Entry>

        var currentTime = (Calendar.getInstance().timeInMillis/1000).toInt()
//        entries = ArrayList<Entry>(values.size)
        for (i in values.indices) {
            chartLineData.addEntry(Entry((dts[i] -currentTime).toFloat(), values[i][index]), index)
        }


        return chartLineData;
    }


    private fun notifyDataChange(chart: LineChart) {
        val data: LineData = chart.getData()
//        data.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()
    }


    fun prepareDataSets(chart: LineChart,configs: List<LineChartDataSetConfig>,  ){
        var lineData: LineData? = chart.data

        lineData?.clearValues()
        for (i in configs.indices) {
            lineData = prepareDataSet(i, configs[i], lineData)


        }
        chart.data = lineData


    }

    private fun prepareDataSet(index: Int, config: LineChartDataSetConfig, pLineData: LineData?): LineData? {
        /* val index = modelLineChart.getIndex(dataType)



         if (index != -1) {
             chartLineData?.removeDataSet(index)
         }*/
        Log.d("MpChartViewBinder", " prepareDataSet: ${index}, $config")

        var chartLineData = pLineData

        var dataSet = createDataSet(index, config)
        //TODO hide and show dataSet
        if (pLineData == null) {
            val sets = java.util.ArrayList<ILineDataSet>()
            sets.add(dataSet)
            chartLineData = LineData(sets)

//            Log.d("MpChartViewBinder", " prepareDataSet 1: ")
        } else {
            val sets: MutableList<ILineDataSet> = pLineData.getDataSets()
            sets.add(dataSet)

            //LOGV(TAG, "addDataSet: size:"+ sets.size());
            //lineData.getDataSets().add(dataSet);

            //lineData.addDataSet(dataSet);
            //lineData.notifyDataChanged();
//            Log.d("MpChartViewBinder", " prepareDataSet 2: ")
        }

        return chartLineData;

    }

    private fun createDataSet(index: Int,   config: LineChartDataSetConfig): ILineDataSet {
        /* val index: Int = modelLineChart.getIndex(dataType)*/
        val label: String = config.label
        val entries: MutableList<Entry>

        var currentTime = (Calendar.getInstance().timeInMillis/1000).toInt()
        entries = ArrayList<Entry>(0)
       /* for (i in values.indices) {
            entries.add(Entry((dts[i] -currentTime).toFloat(), values[i][index]))
        }*/

        val ds = LineDataSet(entries, label)
        ds.lineWidth = 1.4f
        ds.axisDependency = YAxis.AxisDependency.RIGHT
        ds.setDrawCircles(true)
        ds.setDrawValues(true)
        ds.setCircleColor(config.colorOnSurface)
        ds.valueTextColor = config.colorOnSurface
//        ds.isHighlightEnabled = true
//        ds.setDrawHorizontalHighlightIndicator(true)
        ds.color = config.color
        ds.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //LOGV(TAG, "create: dataType: "+ dataTypes+", color: "+ mModelLineChart.getColor(dataTypes));
        return ds
    }
}