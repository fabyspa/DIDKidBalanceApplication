package it.polito.ic2020.did_kidbalanceapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.OnDataPointTapListener
import it.polito.ic2020.did_kidbalanceapplication.database.*
import kotlinx.android.synthetic.main.fragment_graph_g.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GGraphFragment: Fragment(R.layout.fragment_graph_g){

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("bella --> X")
        lateinit var childWeightViewModel: ChildWeightViewModel
        childWeightViewModel = ViewModelProvider(this).get(ChildWeightViewModel::class.java)

        val adapter= ChildWeightsAdapter()
        val recyclerView= rv_weights
        recyclerView.adapter = adapter
        recyclerView.layoutManager= LinearLayoutManager(requireContext())

        //val g = mutableListOf<DataPointInterface>()
        val b = this.arguments
        val idPressed = b?.get("id_pressed").toString().toInt()
        val namePressed = b?.get("name_pressed").toString()
        println(idPressed)

        val no_pesate = resources.getString(R.string.no_pesate)

        val altezza= resources.getString(R.string.height)

        lifecycleScope.launch(Dispatchers.IO) {
            var x: MutableList<Float>
            var date: MutableList<Long>
            var height: Double
            val db: ChildWeightDatabase = ChildWeightDatabase.getInstance(requireContext().applicationContext)
            x = db.childDataBaseDao().getWeightById(idPressed)
            date = db.childDataBaseDao().getDateById(idPressed)
            height = db.childDataBaseDao().getHeightById(idPressed).last()
            height /= 100
            println(x)
            while (x.size > 30) {
                x.removeFirst()
                date.removeFirst()
            }
            withContext(Dispatchers.Main) {
                //fai qui le operazioni sulla GUI
                if(x.size>0){
                    //adapter.setData(x.reversed(),date.reversed())
                    adapt(x, date, adapter)
                    val bmi = (x.last()/(height*height)).toFloat()
                    BMI2.text = bmi.toString()
                } else {
                    intro_pesate.text = no_pesate + " " +namePressed
                    cardViewBMI.visibility= View.GONE
                }
            }


            fun DP(a: Int, b: Float): DataPoint {
                return DataPoint(a.toDouble(), b.toDouble())
            }


            val l = mutableListOf<DataPoint>()

            /* l.removeFirst()
             l.add(DP((System.currentTimeMillis()/1000) as Int,25))
             val p = arrayOf(l.);
             val s = LineGraphSeries<DataPoint>(p)

             */
            while (l.size > 0) l.removeFirst() //needs 30gg
            var maxW: Float = 0F
            for (i in 0 until x.size) {
                // add new DataPoint object to the array for each of your list entries
                //dataPoints[i] = (i, l[i]) // not sure but I think the second argument should be of type double
                l.add(DP(i, x[i]))
                if(x[i]>maxW) maxW=x[i]
            }

            val dataPoints = arrayOfNulls<DataPoint>(x.size) // declare an array of DataPoint objects with the same size as your list

            for (i in 0 until x.size) {
                // add new DataPoint object to the array for each of your list entries
                //dataPoints[i] = (i, l[i]) // not sure but I think the second argument should be of type double
                dataPoints.set(i, l[i])
            }

            withContext(Dispatchers.Main){
                val s = LineGraphSeries(dataPoints) // This one should be obvious right? :)

                s.setColor(Color.CYAN)
                s.setDrawDataPoints(true);
                s.setDataPointsRadius(10F)
                s.setThickness(10)
                s.setDrawBackground(true)
                s.setBackgroundColor(Color.argb(30, 0, 255, 255))
                s.setDrawDataPoints(true)
                s.setDataPointsRadius(20.0F)

                //graph.viewport.isScalable = true
                //graph.viewport.isScrollable = true
                //graph.viewport.isXAxisBoundsManual = true
                //graph.viewport.scrollToStart()
                //graph.viewport.setScalableY(true)


                graph.addSeries(s)

                if (x.size>0) BMI.text = "Last Weight of "+namePressed+": "+(x[x.lastIndex].toString())
                else BMI.text = "No Weights for "+namePressed

                //s.setOnDataPointTapListener(OnDataPointTapListener { series, dataPoint -> Toast.makeText(activity, "Series1: On Data Point clicked: $dataPoint", Toast.LENGTH_SHORT).show() })
            }

            println("Scritto ultimo peso")
            Altezza.text = altezza + " " + height.toString() + "m"

            //SendEmail

            mailToBtn.setOnClickListener {
                sendMail(
                        "Weight of " + namePressed,
                        x.toString(),
                        "",
                        ""
                )
            }
        }
        graph.gridLabelRenderer.isVerticalLabelsVisible = true
        //graph.series.indices
        graph.setBackgroundColor(Color.argb(100, 255, 236, 179))

        nome_b.text=namePressed
    }
    private fun adapt(x: MutableList<Float>, date: MutableList<Long>, adapter: ChildWeightsAdapter){
        adapter.setData(x.reversed(), date.reversed())
    }
    private fun sendMail(subject: String, message: String, sender: String, recipients: String) {

        val wifi_connection= resources.getString(R.string.wifi_connection)

        val mI = Intent(Intent.ACTION_SEND)
        mI.data = Uri.parse("mailto:")
        mI.type = "text/plain"
        mI.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipients))
        mI.putExtra(Intent.EXTRA_SUBJECT, subject)
        mI.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(Intent.createChooser(mI, "Chose Email Client..."))
            Toast.makeText(context, wifi_connection, Toast.LENGTH_LONG).show()
        } catch (e: Exception){
            //vari errori
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }
    private fun goHome(){
        findNavController().navigate(R.id.homeFragment)
    }
}