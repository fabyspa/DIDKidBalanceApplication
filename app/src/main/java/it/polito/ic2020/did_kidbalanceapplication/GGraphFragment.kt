package it.polito.ic2020.did_kidbalanceapplication

import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.DataPointInterface
import com.jjoe64.graphview.series.LineGraphSeries
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGHomeBinding
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGraphGBinding
import kotlinx.android.synthetic.main.fragment_graph_g.*
import kotlinx.android.synthetic.main.fragment_graph_g.view.*
import kotlin.time.days

class GGraphFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGraphGBinding> (inflater, R.layout.fragment_graph_g,container,false)
        println("bella1")

        val g = mutableListOf<DataPointInterface>()

        fun DP(a: Int, b: Int): DataPoint{
            return DataPoint(a.toDouble(), b.toDouble())
        }

        val points = arrayOf( DP (100,18),
            DP (200,21),
            DP(600,19),
            DP(700,24)
        )

        val series = LineGraphSeries<DataPoint>(points)
        series.setColor(Color.CYAN)
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10F)

        binding.graph.addSeries(series)
        binding.graph.setBackgroundColor(Color.argb(100,255,91,0))
        binding.textView.text=("Ultima Pesata:  24Kg")

        println("bella2")

        return binding.root


    }
}