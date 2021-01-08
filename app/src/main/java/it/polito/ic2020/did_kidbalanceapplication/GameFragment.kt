/*
package it.polito.ic2020.did_kidbalanceapplication


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                GameFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}*/

package it.polito.ic2020.did_kidbalanceapplication

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.nio.charset.Charset
import java.io.File
import java.io.BufferedOutputStream
import java.io.DataOutputStream
import java.io.InputStream

class GameFragment: Fragment(R.layout.fragment_game){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("bella1")
        data_from_ESP.text="ciao"
        val manager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        var salvo:Float = 2.0F
        val fileName = "weight_data.txt"

        //set Transport Type to WIFI
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)

        try {
            manager.requestNetwork(builder.build(), object : NetworkCallback(){
                override fun onAvailable(network: Network) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        manager.bindProcessToNetwork(network)
                        Log.d("esp","Network Connected")
                        lifecycleScope.launch(Dispatchers.IO) {
                            val str = URL("http://192.168.4.1/").readText(Charset.forName("UTF-8"))
                            withContext(Dispatchers.Main){
                                data_from_ESP.text = str
                                salvo = str.toFloat()
                               this@GameFragment.context?.openFileOutput("weight_data.txt",Context.MODE_APPEND).use { stream ->
                                   DataOutputStream(BufferedOutputStream(stream)).use { dataOS ->
                                       dataOS.writeChars(str)
                                       println("Bel file scritto")
                                   }
                               }
                            }
                        }
                    } else {
                        ConnectivityManager.setProcessDefaultNetwork(network)
                    }
                    manager.unregisterNetworkCallback(this)
                }
            })
        } catch (e: SecurityException) {
            Log.e(TAG, e.message!!)
        }
        println(salvo)

        //fine Prelievo dati dalla bilancia
        //qui ci va il codice per il gioco mi sa

    }
}
