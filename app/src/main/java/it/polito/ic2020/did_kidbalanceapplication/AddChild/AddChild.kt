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
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.util.Xml
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeight
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightViewModel
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentAddChildBinding
import kotlinx.android.synthetic.main.fragment_add_child.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlSerializer
import java.io.*
import java.net.URL
import java.nio.charset.Charset


class AddChild : Fragment() {

    lateinit var binding: FragmentAddChildBinding
    private lateinit var childWeightViewModel:ChildWeightViewModel
    val xmlSerializer: XmlSerializer = Xml.newSerializer()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        val viewModel by activityViewModels<HomeViewModel>()
        val fileName = "Users.txt"


        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_add_child, container,
                false
        )
        childWeightViewModel = ViewModelProvider(this).get(ChildWeightViewModel::class.java)




        binding.saveName.setOnClickListener {
            Log.i("AddChild", "schiacciato")
            /*  viewModel.addUser(
                    et_name.text.toString()
            )*/
            try {
                insertDataToDatabase()

               /* val name: String = binding.etName.text.toString()
                val height: Int = binding.etAltezza.text.toString().toInt()
                val gender: Boolean = binding.checkBox.text.toString().toBoolean()
                createXMLFile(name, height, gender)
               /* val child = NewChildUser(name, height, gender)
                childs.add(child)*/
                findNavController().navigate(R.id.action_addChild2_to_homeFragment)
          */ }catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
       /* binding.resetButton.setOnClickListener{
            val dir: File = requireContext().filesDir
            if(dir.isDirectory){
                val children = dir.list()
                for (i in children.indices) {
                    File(dir, children[i]).delete()
                }

            }
        }*/
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
                            /*
                            lifecycleScope.launch(Dispatchers.IO) {
                                val str = URL("http://192.168.4.1/").readText(Charset.forName("UTF-8"))
                                // Log.i("Home",str)
                                withContext(Dispatchers.Main) {
                                    //textView2.text = str;
                                }
                            }

                             */

                        } else {
                            ConnectivityManager.setProcessDefaultNetwork(network)
                        }
                        manager.unregisterNetworkCallback(this)
                    }
                })
            } catch (e: SecurityException) {
                Log.e(TAG, e.message!!)
            }
//PER LA CONNESSIONE IN AUTOMATICO NON CANCELLARE
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

    private fun insertDataToDatabase() {
        val firstName = et_name.text.toString()
        val surname = et_surname.text.toString()
        val height = et_altezza.text
        val genderCode = radio_button_group.checkedRadioButtonId
        val picture :Int
        val gender: String

        if(inputCheck(firstName,surname,height,genderCode)){
            if(genderCode==R.id.female_rb) {
                gender= 'F'.toString()
            picture=R.drawable.woman
            }
            else{
                gender= 'M'.toString()

                picture=R.drawable.kid
            }
            val user = ChildWeight(0,firstName,surname, height.toString().toDouble(),gender,picture)

            //Add Data to Database
            childWeightViewModel.addChildWeight(user)
            //Navigate back
            findNavController().navigate((R.id.action_addChild2_to_homeFragment))
            Toast.makeText(requireContext(), "Successfully added!",Toast.LENGTH_LONG ).show()
        }else
        {
            Toast.makeText(requireContext(), "Please fill out all fields",Toast.LENGTH_LONG ).show()

        }

    }

    private fun inputCheck(firstName:String, surname:String, height:Editable,gender:Int):Boolean{
        return !(TextUtils.isEmpty(firstName)&&TextUtils.isEmpty(surname)&&height.isEmpty()&&gender==null)
    }


    private fun createXMLFile(name: String, height: Int, gender: Boolean) {
            val xmlFile = "$name"+"Data"
            Log.i("AddChild", xmlFile)
            val fileos: FileOutputStream = requireContext().openFileOutput(xmlFile, Context.MODE_PRIVATE)
            val writer = StringWriter()
            xmlSerializer.setOutput(writer)
            xmlSerializer.startDocument("UTF-8", true)
            xmlSerializer.startTag(null, name + "_Data")
            xmlSerializer.startTag(null, "childName")
            xmlSerializer.text(name)
            xmlSerializer.endTag(null, "childName")
            xmlSerializer.startTag(null, "height")
            xmlSerializer.text(height.toString())
            xmlSerializer.endTag(null, "height")
            xmlSerializer.startTag(null, "gender")
            xmlSerializer.text(gender.toString())
            xmlSerializer.endTag(null, "gender")
            xmlSerializer.endTag(null, name + "_Data")
            xmlSerializer.endDocument()
            xmlSerializer.flush()
            val dataWrite: String = writer.toString()
            Log.i("AddChild", dataWrite)
            fileos.write(dataWrite.toByteArray())
            fileos.close()

        }


}

