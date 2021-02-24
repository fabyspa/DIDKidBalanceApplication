//package it.polito.ic2020.did_kidbalanceapplication
//
//import android.app.Activity
//import android.content.Context
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//import androidx.navigation.findNavController
//import androidx.navigation.fragment.findNavController
//import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGHomeBinding
//import java.io.*
//
//class GHomeFragment : Fragment() {
//    var savedPin = "1234"
//    var answer = ""
//    override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//        if(readFromFile(requireContext())!=null) {
//            savedPin = readFromFile(requireContext())!!
//            println(savedPin)
//        }
//        else{
//            println("non trovo il file")
//        }
////        answer = File(context?.filesDir?.absolutePath+".txt").readText()
//        println(answer)
//        val binding = DataBindingUtil.inflate<FragmentGHomeBinding>(inflater, R.layout.fragment_g_home, container, false)
//        binding.resetButton.setOnClickListener{
//            reset(this.requireContext())
//        }
//
//        binding.login.setOnClickListener{ view: View ->
//            //val check = binding.pin.text.toString().trim().length in 4..4
//            val pin = binding.pin.text.toString()
//            Log.i("GHome", pin)
//            val pinInt= ConvertIntoNumeric(pin)
//            val savedPinInt= ConvertIntoNumeric(savedPin)
//            if(pinInt == savedPinInt) {
//
//                val b = Bundle()
//                b.putInt("id", 1)
//                println(b)
//
//                val GGraphFragment = Fragment()
//                GGraphFragment.setArguments(b)
//                if (findNavController().currentDestination?.id == R.id.GHomeFragment3) {
//                    view.findNavController().navigate(R.id.action_GHomeFragment3_to_navigation_login)
//                } else {
//                    view.findNavController().navigate(R.id.action_GHomeFragment2_to_child_list_parentFragment)
//                }
//            }
//               // else if(!check) binding.pin.error="inserire 4 numeri"
//            else binding.pin.error="pin non corretto"
//
//        }
//        binding.pinForgot.setOnClickListener{
//            println("pin dimenticato")
//            findNavController().navigate(R.id.action_GHomeFragment2_to_restorePinFragment)
//        }
//        return binding.root
//    }
//    fun reset(context: Context) {
//        Log.i("GHomeFragment", "Reset")
//        if (context is Activity) {
//            val file = File(context.filesDir?.absolutePath, "name.txt")
//            context.deleteDatabase("childWeight_history_database");
//
//            file.delete()
//            context.finish()
//        }
//        Runtime.getRuntime().exit(0)
//    }
//    private fun readFromFile(context: Context): String? {
//        var ret = ""
//        try {
//            val inputStream: InputStream? = context.openFileInput("logIN.txt")
//            if (inputStream != null) {
//                val inputStreamReader = InputStreamReader(inputStream)
//                val bufferedReader = BufferedReader(inputStreamReader)
//                var receiveString: String? = ""
//                val stringBuilder = StringBuilder()
//                while (bufferedReader.readLine().also { receiveString = it } != null) {
//                    stringBuilder.append("\n").append(receiveString)
//                }
//                inputStream.close()
//                ret = stringBuilder.toString()
//            }
//        } catch (e: FileNotFoundException) {
//            Log.e("GRegister", "File not found: " + e.toString())
//        } catch (e: IOException) {
//            Log.e("GRegister", "Can not read file: $e")
//        }
//        return ret
//    }
//
//    private fun ConvertIntoNumeric(xVal: String): Int {
//        return try {
//            xVal.toInt()
//        } catch (ex: Exception) {
//            0
//        }
//    }
//}

package it.polito.ic2020.did_kidbalanceapplication

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGHomeBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_g_home.*
import okio.Utf8
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.File

class GHomeFragment : Fragment() {
    var savedPin = 1234;
    var answer = ""
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val filename = "logIN.txt"
        val file = File(context?.filesDir?.absolutePath, filename)
        if(file.exists()) {
            context?.openFileInput("logIN.txt").use { it ->
                DataInputStream(it).use { dis ->
                    while (dis.available() > 0) {
                        savedPin = dis.readInt()
                        //answer = dis.readLine()
                        //println(answer)
                    }
                    println(savedPin)
                }
            }
        }
//        answer = File(context?.filesDir?.absolutePath+".txt").readText()
        //   println(answer)
        val binding = DataBindingUtil.inflate<FragmentGHomeBinding>(inflater, R.layout.fragment_g_home, container, false)
        binding.resetButton.setOnClickListener{
            reset(this.requireContext())
        }
        binding.login.setOnClickListener { view: View ->
            val check = binding.pin.text.toString().trim().length in 4..4
            val pin = binding.pin.text.toString()
            fun hideKeyboard() {
                val i = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                i.hideSoftInputFromWindow(view.windowToken, 0)
            }
            if (pin == savedPin.toString() && check) {

                val b = Bundle()
                b.putInt("id", 1)
                println(b)

                val GGraphFragment = Fragment()
                GGraphFragment.setArguments(b)
                hideKeyboard()
                if (findNavController().currentDestination?.id == R.id.GHomeFragment3) {
                    view.findNavController().navigate(R.id.action_GHomeFragment3_to_navigation_login)
                } else {
                    view.findNavController().navigate(R.id.action_GHomeFragment2_to_child_list_parentFragment)
                }

                //view.findNavController().navigate(R.id.action_GHomeFragment2_to_GGraphFragment2)
            } else if (!check) binding.pin.error = "inserire 4 numeri"
            else binding.pin.error = "pin non corretto"

        }
        binding.pinForgot.setOnClickListener {
            println("pin dimenticato")
            findNavController().navigate(R.id.action_GHomeFragment2_to_restorePinFragment)
        }
        return binding.root
    }
    fun reset(context: Context) {
        Log.i("GHomeFragment", "Reset")
        if (context is Activity) {
            val file = File(context.filesDir?.absolutePath, "name.txt")
            context.deleteDatabase("childWeight_history_database");

            file.delete()
            context.finish()
        }
        Runtime.getRuntime().exit(0)
    }

}
