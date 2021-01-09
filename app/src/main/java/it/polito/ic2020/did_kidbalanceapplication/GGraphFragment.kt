package it.polito.ic2020.did_kidbalanceapplication

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_graph_g.*
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.*

/*class GGraphFragment : Fragment(){
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

 */

class GGraphFragment: Fragment(R.layout.fragment_graph_g){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("bella1")

        //val g = mutableListOf<DataPointInterface>()

        fun DP(a: Int, b: Int): DataPoint{
            return DataPoint(a.toDouble(), b.toDouble())
        }

  /*      val points = arrayOf(DP((System.currentTimeMillis() / 1000) as Int, 18),
                DP(200, 21),
                DP(600, 19),
                DP(700, 24)
        )

   */
        val l = mutableListOf<DataPoint>()
        l.add(DP(Calendar.DAY_OF_MONTH-1, 18))
        l.add(DP(Calendar.DAY_OF_MONTH-2, 20))
        l.add(DP(Calendar.DAY_OF_MONTH-1, 22))
        l.add(DP(Calendar.DAY_OF_MONTH as Int, 24))
        l.add(DP(Calendar.DAY_OF_MONTH+1, 24))
        l.add(DP(Calendar.DAY_OF_MONTH+2, 25))
        l.add(DP(Calendar.DAY_OF_MONTH+3, 26))
        l.add(DP(Calendar.DAY_OF_MONTH+4, 22))
        l.add(DP(Calendar.DAY_OF_MONTH+5, 22))
        l.add(DP(Calendar.DAY_OF_MONTH+6, 27))
        l.add(DP(Calendar.DAY_OF_MONTH+7, 28))
        l.add(DP(Calendar.DAY_OF_MONTH+8, 26))
        l.add(DP(Calendar.DAY_OF_MONTH+9, 27))
        l.add(DP(Calendar.DAY_OF_MONTH+10, 28))
        l.add(DP(Calendar.DAY_OF_MONTH+11, 28))
        l.add(DP(Calendar.DAY_OF_MONTH+12, 28))
        l.add(DP(Calendar.DAY_OF_MONTH+13, 27))
        l.add(DP(Calendar.DAY_OF_MONTH+14, 27))
        l.add(DP(Calendar.DAY_OF_MONTH+15, 27))
        l.add(DP(Calendar.DAY_OF_MONTH+16, 27))
        l.add(DP(Calendar.DAY_OF_MONTH+17, 22))
        l.add(DP(Calendar.DAY_OF_MONTH+18, 29))
        l.add(DP(Calendar.DAY_OF_MONTH+19, 29))
        l.add(DP(Calendar.DAY_OF_MONTH+20, 28))
        l.add(DP(Calendar.DAY_OF_MONTH+21, 27))
        l.add(DP(Calendar.DAY_OF_MONTH+22, 25))
        l.add(DP(Calendar.DAY_OF_MONTH+23, 26))
        l.add(DP(Calendar.DAY_OF_MONTH+24, 25))
       /* l.removeFirst()
        l.add(DP((System.currentTimeMillis()/1000) as Int,25))
        val p = arrayOf(l.);
        val s = LineGraphSeries<DataPoint>(p)

        */
        while(l.size>10) l.removeFirst() //needs 30gg

        val dataPoints = arrayOfNulls<DataPoint>(l.size) // declare an array of DataPoint objects with the same size as your list

        for (i in 0 until l.size) {
            // add new DataPoint object to the array for each of your list entries
            //dataPoints[i] = (i, l[i]) // not sure but I think the second argument should be of type double
            dataPoints.set(i,l[i])
        }

        val s = LineGraphSeries(dataPoints) // This one should be obvious right? :)

        println("l")
        println(l)
        println("dataP")
        println(dataPoints)
        println("s")
        println(s)

        /*val series = LineGraphSeries<DataPoint>(points)
        series.setColor(Color.CYAN)
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10F)
         */
        s.setColor(Color.CYAN)
        s.setDrawDataPoints(true);
        s.setDataPointsRadius(10F)

        //graph.addSeries(series)
        graph.addSeries(s)
        graph.setBackgroundColor(Color.argb(100,255,236,179))
        textView.text=(l[l.lastIndex].toString())

        println("bella2")


        //check FIle
        val tmp:MutableList<Float> = mutableListOf()
        val f = "weight_data.txt"
        context?.openFileInput("weight_data.txt").use {
                it ->
            DataInputStream(it). use {
                    dis ->
                while (dis.available()>0){
                    tmp.add(dis.readFloat())
                    //per la data, sarebbe bene inserirla al momento della scrittura nel file
                    //in lettura si leggerà data - peso e si mette diretto nella lista DataPoints
                    println("bel file letto")
                }
            }
        }
        while (tmp.size>2) {
            tmp.removeFirst()
        }
        textView.text=tmp.toString()
    }
}