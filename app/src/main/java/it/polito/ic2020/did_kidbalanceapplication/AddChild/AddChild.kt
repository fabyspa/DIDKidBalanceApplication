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
import android.util.Xml
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
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
import org.xmlpull.v1.XmlSerializer
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.StringWriter
import java.net.URL
import java.nio.charset.Charset


class AddChild : Fragment(R.layout.fragment_add_child) {

    lateinit var binding: FragmentAddChildBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?{


        val viewModel by activityViewModels<HomeViewModel>()
        val fileName = "Users.txt"
        val xmlFile = "userData"
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_add_child, container,
                false
        )





        binding.saveName.setOnClickListener {
            viewModel.addUser(
                    et_name.text.toString()
            )
            try {
                //val fos = FileOutputStream("userData.xml")
                val fileos: FileOutputStream = requireContext().openFileOutput(xmlFile, Context.MODE_PRIVATE)
                val xmlSerializer: XmlSerializer = Xml.newSerializer()
                val writer = StringWriter()
                xmlSerializer.setOutput(writer)
                xmlSerializer.startDocument("UTF-8", true)
                xmlSerializer.startTag(null, "userData")
                xmlSerializer.startTag(null, "userName")
                xmlSerializer.text(binding.etName.text.toString())

                xmlSerializer.endTag(null, "userName")
                xmlSerializer.startTag(null, "password")
                xmlSerializer.text("caccola")
                xmlSerializer.endTag(null, "password")
                xmlSerializer.endTag(null, "userData")
                xmlSerializer.endDocument()
                xmlSerializer.flush()
                val dataWrite: String = writer.toString()
                fileos.write(dataWrite.toByteArray())
                fileos.close()
            } catch (e: FileNotFoundException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: IllegalStateException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
//            context?.openFileInput(fileName).use { stream ->
//                val text = stream?.bufferedReader().use {
//                    it?.readText()
//                }
//                val fileBody = et_name.text.toString()
//
//                context?.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
//                    output?.write(fileBody.toByteArray())
//                }
                /*
            context?.openFileInput(fileName).use { stream ->
                val text = stream?.bufferedReader().use {
                    it?.readText()

                }

                Log.d("TAG", "LOADED: $text")
            }*/


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
                            Log.d("esp", "network connected")
                            lifecycleScope.launch(Dispatchers.IO) {
                                val str = URL("http://192.168.4.1/").readText(Charset.forName("UTF-8"))
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


       // }
        return binding.root
    }

}