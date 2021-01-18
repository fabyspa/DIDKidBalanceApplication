package it.polito.ic2020.did_kidbalanceapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGHomeBinding
import okio.Utf8
import java.io.DataInputStream

class GHomeFragment : Fragment() {
    var savedPin = 1234;
    var answer = ""
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        context?.openFileInput("logIN.txt").use { it ->
            DataInputStream(it). use { dis ->
                while (dis.available()>0){
                    savedPin = dis.readInt()
                    answer = dis.readLine()
                    println(answer)
                }
                println(savedPin)
            }
        }
        val binding = DataBindingUtil.inflate<FragmentGHomeBinding>(inflater, R.layout.fragment_g_home, container, false)
        binding.login.setOnClickListener{ view: View ->
            val check = binding.pin.text.toString().trim().length in 4..4
            val pin = binding.pin.text.toString()
            if(pin == savedPin.toString() && check) {

                val b = Bundle()
                b.putInt("id", 1)
                println(b)

                val GGraphFragment = Fragment()
                GGraphFragment.setArguments(b)

                view.findNavController().navigate(R.id.action_GHomeFragment2_to_GGraphFragment2)
            } else if(!check) binding.pin.error="inserire 4 numeri"
            else binding.pin.error="pin non corretto"

        }
        binding.pinForgot.setOnClickListener{
            println("pin dimenticato")
            findNavController().navigate(R.id.action_GHomeFragment2_to_restorePinFragment)
        }
        return binding.root
    }
}
