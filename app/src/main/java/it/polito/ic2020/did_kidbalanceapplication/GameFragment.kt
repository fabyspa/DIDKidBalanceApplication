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
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightViewModel
import it.polito.ic2020.did_kidbalanceapplication.database.GameWeight
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets


class GameFragment: Fragment(R.layout.fragment_game) {
//    internal class IOAsyncTask : AsyncTask<String, Void?, String>() {
//
//        override fun onPostExecute(response: String) {
//            Log.d("networking", response)
//        }
//
////        override fun doInBackground(vararg params: String): String {
////           //return GameFragment().sendData("Ciaooo")!!;
////            return null
////        }
//    }
    lateinit var childWeightViewModel: ChildWeightViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("bella1")
        data_from_ESP.text = "Connettiti alla rete WiFi 'KidBalance'"
        val manager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        var salvo: Float = 2.0F
        val fileName = "weight_data.txt"
        childWeightViewModel = ViewModelProvider(this).get(ChildWeightViewModel::class.java)


        //set Transport Type to WIFI
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        //prelevo peso
        try {
            manager.requestNetwork(builder.build(), object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        manager.bindProcessToNetwork(network)
                        Log.d("esp", "Network Connected")
                        lifecycleScope.launch(Dispatchers.IO) {
                            val str = URL("http://192.168.4.1/c").readText(Charset.forName("UTF-8"))
                            withContext(Dispatchers.Main) {
                                fun insertGameWeightToDatabase(){
                                    val weight= str.toFloat()
                                    val date= System.currentTimeMillis()
                                    val id= 1

                                    val gameWeight= GameWeight(id,date,weight)
                                    childWeightViewModel.addGameWeight(gameWeight)
                                }
                                data_from_ESP.text = str
                                // salvo = str.toFloat()
                                //inserire salvo in database
                                insertGameWeightToDatabase()
                                this@GameFragment.context?.openFileOutput(
                                    "weight_data.txt",
                                    Context.MODE_APPEND
                                ).use { stream ->
                                    DataOutputStream(BufferedOutputStream(stream)).use { dataOS ->
                                        dataOS.writeFloat(str.toFloat())
                                        println(str.toFloat())
                                        println("Bel file scritto")
                                        println(str.toFloat().toString())
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

        send.setOnClickListener {
            /*
                    val urlString = "http://192.168.4.1/" // URL to call
            val data = "prova invio" //data to post
            var out: OutputStream? = null
                val url = URL(urlString)
                val urlConnection: HttpURLConnection =
                            url.openConnection() as HttpURLConnection
                out = BufferedOutputStream(urlConnection.outputStream)
                try {
                    val writer = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
                    println("")
                    writer.write(data)
                    writer.flush()
                    writer.close()
                    out.close()
                    urlConnection.connect()
                }catch (e: Exception) {
                    println(e.message)
                }


            */
            //val httpclient: HttpClient = DefaultHttpClient()
            //val httppost = HttpPost("LINK TO SERVER")
            //IOAsyncTask().execute("CIAOOOOO")

        }



        //fine Prelievo dati dalla bilancia
        //qui ci va il codice per il gioco mi sa

        //invio flag alla ESP

        fun post(url: String, body: String) {
            lifecycleScope.launch(Dispatchers.IO) {
                return@launch URL("http://192.168.4.1/c")
                        .openConnection()
                        .let {
                            it as HttpURLConnection
                        }.apply {
                            setRequestProperty("Content-Type", "text/html")
                            requestMethod = "POST"

                            doOutput = true
                            val outputWriter = OutputStreamWriter(outputStream.buffered())
                            outputWriter.write(body)
                            outputWriter.flush()
                        }.let {
                            if (it.responseCode == 200) it.inputStream else it.errorStream
                        }.let { streamToRead ->
                            println("streamToRead.toString()")
                            BufferedReader(InputStreamReader(streamToRead)).use {
                                val response = StringBuffer()
                                var inputLine = it.readLine()
                                println(inputLine)
                                //inputLine = "testo scritto a mano"
                                while (inputLine != null) {
                                    println("sono dentro inputline")
                                    response.append(inputLine)
                                    inputLine = it.readLine()
                                }
                                it.close()
                                response.toString()
                                println(response.toString())
                            }
                        }
            }

        }

//        fun sendData(message: String): String? {
//            return try {
//
//                val request: Request = Request.Builder()
//                        .url("http://192.168.4.1/c")
//                        .header("Connection", "close")
//                        .post(message.toRequestBody(MEDIA_TYPE_MARKDOWN))
//                        .build()
//                val response: Response = client.newCall(request).execute()
//                response.body?.string()
//            } catch (e: IOException) {
//                "Error: " + e.message
//            }

            send.setOnClickListener {
                /*
                    val urlString = "http://192.168.4.1/" // URL to call
                    val data = "prova invio" //data to post
                    var out: OutputStream? = null
                    try {
                        val url = URL(urlString)
                        val urlConnection: HttpURLConnection =
                            url.openConnection() as HttpURLConnection
                        out = BufferedOutputStream(urlConnection.getOutputStream())
                        println("urlConnected")
                        val writer = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
                        writer.write(data)
                        writer.flush()
                        writer.close()
                        out.close()
                        urlConnection.connect()
                    } catch (e: Exception) {
                        println(e.message)
                    }
            */
                //val httpclient: HttpClient = DefaultHttpClient()
                //val httppost = HttpPost("LINK TO SERVER")
                post("http://192.168.4.1/c", "Check \n")

            }
        }
//    companion object {
//        val MEDIA_TYPE_MARKDOWN = "text/x-markdown; charset=utf-8".toMediaType()
//    }

    }






