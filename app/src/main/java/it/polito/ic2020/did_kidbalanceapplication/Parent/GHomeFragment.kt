package it.polito.ic2020.did_kidbalanceapplication.Parent

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
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGHomeBinding
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
        val navHostFragment = (this.activity?.supportFragmentManager?.findFragmentById(R.id.main_activity_container) as NavHostFragment)
        val inflaterGraph = navHostFragment.navController.navInflater
        val graph = inflaterGraph.inflate(R.navigation.navigation_login)
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

        val wrong_pin = resources.getString(R.string.wrong_pin)
        val wrong_count_pin = resources.getString(R.string.wrong_count_pin)
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
            } else if (!check) binding.pin.error = wrong_count_pin
            else binding.pin.error = wrong_pin

        }
        binding.pinForgot.setOnClickListener {
            println("pin dimenticato")
            if(findNavController().currentDestination?.id== R.id.GHomeFragment3)
            {
                findNavController().navigate(R.id.action_GHomeFragment3_to_navigation_login).also {
                    graph.startDestination= R.id.restorePinFragment
                    navHostFragment.navController.graph = graph
                }

            }
            else
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
