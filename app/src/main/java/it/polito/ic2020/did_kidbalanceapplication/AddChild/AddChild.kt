package it.polito.ic2020.did_kidbalanceapplication.AddChild

import android.app.DatePickerDialog
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
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.MainActivity
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
import java.util.*


class AddChild : Fragment() {

    lateinit var binding: FragmentAddChildBinding
    private lateinit var childWeightViewModel:ChildWeightViewModel


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
        binding.saveName.isEnabled=false
        var counter = 0

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)
        binding.calendar.setOnClickListener{
            val dpd = context?.let { it1 ->
                DatePickerDialog(it1, DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, month, dayOfMonth ->
                    et_compleanno.setText(""+ dayOfMonth +"/"+month+"/"+year+"")
                }, year, month, dayOfMonth)
            }
            dpd?.show()
            counter++
            if (counter==5){
                binding.saveName.isEnabled=true
            }
        }
        binding.etName.doAfterTextChanged {
            counter++
            println("et name "+counter)
            if(counter==5){
                binding.saveName.isEnabled=true
            }
        }

        binding.etAltezza.doAfterTextChanged {
            counter++
            println("et altezza "+counter)
            if(counter==5){
                binding.saveName.isEnabled=true
            }
        }

        binding.etSurname.doAfterTextChanged {
            counter++
            println("et altezza2 "+counter)
            if(counter==5){
                binding.saveName.isEnabled=true
            }
        }

        if(binding.maleRb.isChecked){
            counter++
            println("malechecked "+counter)
            if(counter==5){
                binding.saveName.isEnabled=true
            }
        }

        if(binding.femaleRb.isChecked){
            counter++
            println("femalechecked "+counter)
            if(counter==5){
                binding.saveName.isEnabled=true
            }
        }

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
                picture=R.drawable.ic_f
            }
            else{
                gender= 'M'.toString()
                picture=R.drawable.ic_m
            }
            val user = ChildWeight(0,firstName,surname, height.toString().toDouble(),gender,picture)

            //Add Data to Database
            childWeightViewModel.addChildWeight(user)
            //Navigate back
                    // findNavController().navigate((R.id.action_addChild2_to_homeFragment))
            //new child?
            val alert_intro = ""
            val alert_text = "Vuoi aggiungere un altro bambino?"
            val yes_text = resources.getString(R.string.yes_text)
            val no_text = resources.getString(R.string.no_text)

            //Toast.makeText(requireContext(), "Vuoi aggiungere un altro bambino?",Toast.LENGTH_LONG ).show()
            val alert = AlertDialog.Builder(requireContext())
            alert.setTitle(alert_intro)
            alert.setMessage(alert_text)
            //alert.setPositiveButton("Ok", DialogInterface.OnClickListener(function = x))
            alert.setPositiveButton(yes_text){ dialog, witch -> findNavController().navigate(R.id.action_addChild2_to_reloadAddChild)
            }
            alert.setNegativeButton(no_text){ dialog, which -> findNavController().navigate(R.id.action_addChild2_to_homeFragment)
            }
            alert.show()
        }else
        {
            Toast.makeText(requireContext(), "Please fill out all fields",Toast.LENGTH_LONG ).show()

        }

    }

    private fun inputCheck(firstName:String, surname:String, height:Editable,gender:Int):Boolean{
        return !(TextUtils.isEmpty(firstName)&&TextUtils.isEmpty(surname)&&height.isEmpty()&&gender==null)
    }





}

