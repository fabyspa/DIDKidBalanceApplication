package it.polito.ic2020.did_kidbalanceapplication

import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.Game.GameLib
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightDatabase
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightViewModel
import it.polito.ic2020.did_kidbalanceapplication.database.GameWeight
import kotlinx.android.synthetic.main.fragment_circle_game.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.activity_child.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.lang.String
import java.net.URL
import java.nio.charset.Charset
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit

class GameCircle : Fragment(R.layout.fragment_circle_game) {
    //Need config
    private var nb_bloc_start = 1
    private var nb_bloc_4_win = 100
    private var default_life = 5
    private var poids_du_mode = 1.toDouble()
    private var score = 0.0
    private var chrono = false
    private var lvl = 1
    lateinit var childWeightViewModel: ChildWeightViewModel
    val planets = listOf("Moon", "Mars", "Jupiter","Saturn","Uranus","Neptune")

    fun onBoardingFinished(): Boolean{
        val id = requireActivity().intent!!.extras?.get("id").toString().toInt()
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding"+id, Context.MODE_PRIVATE)
        println("from game circle_ "+sharedPreferences.getBoolean("Finished", false))
        return sharedPreferences.getBoolean("Finished", false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = requireActivity().intent!!.extras?.get("id").toString().toInt()
        println(id)
        if(!onBoardingFinished()){
            //Toast.makeText(this.context, "Wlakthrough",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_gameCircle2_to_viewPagerFragment2)
        }
        val extras: Bundle? = this.arguments
        if (extras != null) {
            nb_bloc_start = extras.getInt("nb_bloc_start", 1)
            nb_bloc_4_win = extras.getInt("nb_bloc_4_win",100)
            default_life = extras.getInt("default_life", 5)
            poids_du_mode = extras.getDouble("poids_du_mode", 1.toDouble())
            lvl = extras.getInt("lvl", 1)
            score = extras.getDouble("score", 0.0)
            chrono = extras.getBoolean("chrono", false)
        }
        //if (chrono) findViewById(R.id.timer).setVisibility(View.VISIBLE)
        val numButton: Int = lvl + 3
        val arrayColor = intArrayOf(
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.CYAN,
                Color.MAGENTA,
                Color.rgb(255, 102, 204),
                Color.rgb(102, 51, 51),
                Color.rgb(51, 51, 0),
                Color.rgb(102, 153, 153)
        )
        val buttons: MutableList<Button> = ArrayList()
        val main: FrameLayout = view.findViewById(R.id.main)
        val displayMetrics = DisplayMetrics()
        activity?.getWindowManager()?.getDefaultDisplay()?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        val size: Int
        size = if (height <= width) {
            height
        } else {
            width
        }
        for (i in 0 until numButton) {
            /* Create some quick TextViews that can be placed. */
            val button = Button(this.context)
            buttons.add(button)

            // Set a text and center it in each view.
            //button.setText(getString(R.string.couleur) + i)
            button.gravity = Gravity.CENTER
            button.setBackgroundColor(arrayColor[i])

            // Force the views to a nice size (150x100 px) that fits my display.
            // This should of course be done in a display size independent way.
            val lp = FrameLayout.LayoutParams(
                    (size * 0.30).toInt(),
                    (size * 0.15).toInt()
            )

            // Place all views in the center of the layout. We'll transform them
            // away from there in the code below.
            lp.gravity = Gravity.CENTER

            // Set layout params on view.
            button.layoutParams = lp

            // Calculate the angle of the current view. Adjust by 90 degrees to
            // get View 0 at the top. We need the angle in degrees and radians.
            val angleDeg = i * 360.0f / numButton - 90.0f
            val angleRad = (angleDeg * Math.PI / 180.0f).toFloat()

            // Calculate the position of the view, offset from center (300 px from
            // center). Again, this should be done in a display size independent way.
            button.setTranslationX(
                    ((size * 0.3 * Math.cos(angleRad.toDouble()).toFloat()).toInt()).toFloat()
            )
            button.setTranslationY(
                    ((size * 0.3 * Math.sin(angleRad.toDouble()).toFloat()).toInt()).toFloat()
            )

            // Set the rotation of the view.
            button.rotation = angleDeg + 90.0f
            main?.addView(button)
        }

/*
        //Get some shit on layout
        val lbl_score: TextView? = view?.findViewById(R.id.lbl_score)
        val lbl_life: TextView? = view?.findViewById(R.id.lbl_life)
        val btn_start: Button? = view?.findViewById(R.id.btn_start)
        val lbl_round: TextView? = view?.findViewById(R.id.lbl_round)
        val lbl_timer: TextView? = view?.findViewById(R.id.timer)
        val lbl_lvl: TextView? = view?.findViewById(R.id.lbl_lvl)

 */
        //lbl_lvl.setText(getString(R.string.level) + lvl)
        Log.e("score", score.toString())
        //Setup class
        val game = GameLib(
                nb_bloc_start,
                nb_bloc_4_win,
                default_life,
                poids_du_mode,
                buttons,
                arrayColor,
                lvl,
                lbl_score,
                lbl_life,
                btn_start,
                lbl_round,
                score,
                chrono,
                timer,
                returnHome,
                hide_game2
        )

        returnHome.setOnClickListener {
            println("return Home CLiccato")
            findNavController().navigate(R.id.BHomeFragment2)
        }

        //quantique
        val thread: Thread = object : Thread() {
            override fun run() {
                do {
                    try {
                        TimeUnit.SECONDS.sleep(1)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                } while (!game.end)
                if (game.win) {
                    //hide_game2.visibility = View.VISIBLE
                    //get next view  for game
                    println("game.win =1")
                    lifecycleScope.launch(Dispatchers.IO){
                        val id = requireActivity().intent!!.extras?.get("id").toString().toInt()

                        val db: ChildWeightDatabase = ChildWeightDatabase.getInstance(requireContext().applicationContext)
                        var bambinone = db.childDataBaseDao().getAllChildData(id)
                        println(bambinone)
                        bambinone.punteggio=(bambinone.punteggio+game.score.toInt()*1.5).toInt()
                        db.childDataBaseDao().update(bambinone)

                    }
                    //startActivityForResult(startGame, lvl + 1)
                    //Toast.makeText(context,"Avanzamento verso nuovo pianeta",Toast.LENGTH_LONG).show()
                    Log.i("scorend", String.valueOf(game.score))
                } else {
                    //hide_game2.visibility = View.VISIBLE
                    val intent = Intent()
                    intent.putExtra("exit", true)
                    lifecycleScope.launch(Dispatchers.IO){
                        val id = requireActivity().intent!!.extras?.get("id").toString().toInt()

                        val db: ChildWeightDatabase = ChildWeightDatabase.getInstance(requireContext().applicationContext)
                        var bambinone = db.childDataBaseDao().getAllChildData(id)
                        println(bambinone)
                        bambinone.punteggio=(bambinone.punteggio+game.score.toInt()*1.8).toInt()
                        withContext(Dispatchers.Main) {
                            activity?.progressBar2?.progress = (bambinone.punteggio+game.score.toInt()*1.8).toInt()
                        }
                        db.childDataBaseDao().update(bambinone)
                        if(bambinone.punteggio>=100){
                            println("NEXTPLANET")
                            bambinone.punteggio=bambinone.punteggio-100;
                            withContext(Dispatchers.Main) {
                                activity?.progressBar2?.progress = bambinone.punteggio-100
                                activity?.pianeta_destinazione?.text = planets[planets.indexOf(bambinone.planet)+1]
                            }
                            val previewsPlanet= planets.indexOf(bambinone.planet)
                            bambinone.planet= planets[previewsPlanet+1]
                            db.childDataBaseDao().update(bambinone)
                        }

                    }
                    //setResult(RESULT_OK, intent)
                    //finish()
                    //Toast.makeText(context, "Maybe you're a looser", Toast.LENGTH_LONG).show()
                }
            }
        }
        thread.start()

        val callback: OnBackPressedCallback =
                object : OnBackPressedCallback(true /* enabled by default */) {
                    override fun handleOnBackPressed() {
                        // Handle the back button event
                        findNavController().navigate(R.id.BHomeFragment2)
                        //activity?.finish()
                    }
                }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)





        //data_from_ESP.text = "Connettiti alla rete WiFi 'KidBalance'"
        val manager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        val fileName = "weight_data.txt"
        var salvo = 0F
        childWeightViewModel = ViewModelProvider(this).get(ChildWeightViewModel::class.java)


        //set Transport Type to WIFI
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        //prelevo peso
        try {
            manager.requestNetwork(builder.build(), object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        manager.bindProcessToNetwork(network)
                        Log.d("esp", "Network Connected")
                        lifecycleScope.launch(Dispatchers.IO) {
                            val str = URL("http://192.168.4.1/").readText(Charset.forName("UTF-8"))
                            withContext(Dispatchers.Main) {
                                //data_from_ESP.text = str
                                //salvo = str.toFloat()
                                //inserire salvo in database
                                this@GameCircle.context?.openFileOutput(
                                    "weight_data.txt",
                                    Context.MODE_APPEND
                                ).use { stream ->
                                    DataOutputStream(BufferedOutputStream(stream)).use { dataOS ->
                                        dataOS.writeFloat(str.toFloat())
                                        //println(str.toFloat())
                                        //println("Bel file scritto")
                                    }
                                }
                            }
                            fun insertGameWeightToDatabase() {
                                val filename = "weight_data.txt"
                                var file = File(context?.filesDir?.absolutePath, filename)
                                var fileExists = file.exists()
                                if(fileExists){
                                    context?.openFileInput("weight_data.txt").use { it ->
                                        DataInputStream(it).use { dis ->
                                            while (dis.available() > 0) {
                                                salvo = dis.readFloat()
                                                println(salvo)
                                                println("bel file letto")
                                            }
                                        }
                                    }
                                    val weight = str.toString().toFloat()//salvo
                                    print("salvo: ")
                                    println(salvo)
                                    println("weight: "+weight)
                                    val date = System.currentTimeMillis()
                                    val id = activity!!.intent!!.extras?.get("id").toString().toInt()
                                    println("id  from extra  " + id)

                                    val gameWeight = GameWeight(id, date, weight)
                                    childWeightViewModel.addGameWeight(gameWeight)
                                }
                            }
                            insertGameWeightToDatabase()
                        }
                        //insertGameWeightToDatabase()
                    } else {
                        ConnectivityManager.setProcessDefaultNetwork(network)
                    }
                    manager.unregisterNetworkCallback(this)
                }
            })
        } catch (e: SecurityException) {
            Log.e(ContentValues.TAG, e.message!!)
        }
        println("salvo var:  "+salvo)

    }
/*
    internal fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == lvl + 1) {
            if (resultCode == RESULT_OK) {
                val exit = data.extras!!.getBoolean("exit")
                if (exit) {
                    val intent = Intent()
                    intent.putExtra("exit", true)
                    //setResult(RESULT_OK, intent)
                    //finish()
                }
            }
        }
    }

    fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("exit", true)
        //setResult(RESULT_OK, intent)
        //finish()
    }
*/

}