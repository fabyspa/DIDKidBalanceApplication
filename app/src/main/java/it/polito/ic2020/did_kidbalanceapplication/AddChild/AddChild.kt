package it.polito.ic2020.did_kidbalanceapplication.AddChild

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentAddChildBinding
import kotlinx.android.synthetic.main.fragment_add_child.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.nio.charset.Charset


class AddChild : Fragment(R.layout.fragment_add_child) {

    lateinit var binding: FragmentAddChildBinding
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val viewModel by activityViewModels<HomeViewModel>()

        saveName.setOnClickListener{
            viewModel.addUser(
                    et_name.text.toString()
            )
            findNavController().navigate(R.id.action_addChild_to_home2)

        }
        val manager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val builder = NetworkRequest.Builder()

//set the transport type to WIFI

//set the transport type to WIFI
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)

        try {
            manager.requestNetwork(builder.build(), object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        manager.bindProcessToNetwork(network)
                        Log.d("esp","network connected")
                        lifecycleScope.launch(Dispatchers.IO) {
                            val str= URL("http://192.168.4.1/").readText(Charset.forName("UTF-8"))
                            // Log.i("Home",str)
                            withContext(Dispatchers.Main) {
                                textView2.text = str;
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
        // Inflate the layout for this fragment
        /*binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_child,container,
            false*/
        /*val manager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val builder = NetworkRequest.Builder()
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        builder.removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        builder.setNetworkSpecifier(
                WifiNetworkSpecifier.Builder().apply {
                    setSsid("ESPap")
                    setWpa2Passphrase("thereisnospoon")
                }.build()
        )
        Log.d("esp", "Builder built")

//set the transport type to WIFI

//set the transport type to WIFI

        try {
            manager.requestNetwork(builder.build(), object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    manager.bindProcessToNetwork(network)
                    Log.d("esp","network connected")
                    lifecycleScope.launch(Dispatchers.IO) {
                        val str= URL("http://192.168.4.1/").readText(Charset.forName("UTF-8"))
                       // Log.i("Home",str)
                        withContext(Dispatchers.Main) {
                            textView2.text = str;
                        }
                    }

                }
            })
        } catch (e: SecurityException) {
            Log.e("Ciao", e.message!!)
        }*/



    }

}