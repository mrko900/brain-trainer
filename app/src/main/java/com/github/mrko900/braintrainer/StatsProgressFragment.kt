package com.github.mrko900.braintrainer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mrko900.braintrainer.databinding.StatsProgressBinding
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class StatsProgressFragment : Fragment() {
    private lateinit var binding: StatsProgressBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (activity == null) {
            Log.e(LOGGING_TAG, "activity must be non-null")
            throw IllegalStateException("activity must be non-null")
        }
        mainActivity = activity as MainActivity
        binding = StatsProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setSupportActionBar(binding.toolbar)
        mainActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mainActivity.supportActionBar!!.setDisplayShowHomeEnabled(true)
        mainActivity.onBackPressedCallback = Runnable {
            mainActivity.navigation.navigate(
                R.id.fragment_stats,
                navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right_fade_in)
                    .setExitAnim(R.anim.slide_out_right_fade_out)
                    .build(),
                args = null
            )
        }
        mainActivity.supportActionBar!!.title = mainActivity.getString(R.string.stats_progress)

        val data = ArrayList<Entry>()
        data.add(Entry((GregorianCalendar(2023, 3, 3).time.time / 86400000L).toFloat(), 1f))
        data.add(Entry((GregorianCalendar(2023, 3, 4).time.time / 86400000L).toFloat(), 2f))
        data.add(Entry((GregorianCalendar(2023, 3, 5).time.time / 86400000L).toFloat(), -1f))
        data.add(Entry((GregorianCalendar(2023, 3, 6).time.time / 86400000L).toFloat(), 7f))
        val chart = binding.chart
        val dataSet = LineDataSet(data, "Test")
        dataSet.circleRadius = 4f
        dataSet.circleHoleRadius = 2.5f
        dataSet.lineWidth = 2f
        dataSet.setDrawValues(false)
        chart.legend.isEnabled = false
        chart.description.isEnabled = false
        chart.data = LineData(dataSet)
        chart.xAxis.setDrawGridLines(false)
        val xAxis = binding.chart.xAxis
        xAxis.granularity = 1f
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                val date = Calendar.getInstance()
                date.time = Date(86400000L * value.toLong())
                val month = date.get(Calendar.MONTH)
                val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
                val monthName = months[month]
                val dayOfMonth = date.get(Calendar.DAY_OF_MONTH).toString()
                return "$monthName $dayOfMonth"
            }
        }
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        chart.invalidate()
    }
}
