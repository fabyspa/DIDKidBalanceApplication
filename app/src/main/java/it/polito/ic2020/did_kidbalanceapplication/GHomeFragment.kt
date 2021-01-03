package it.polito.ic2020.did_kidbalanceapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGHomeBinding
import kotlinx.android.synthetic.main.fragment_g_home.*

class GHomeFragment : Fragment() {
    val savedPin = 1234;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGHomeBinding> (inflater, R.layout.fragment_g_home,container,false)
        binding.login.setOnClickListener{
            view : View ->
            val check = binding.pin.text.toString().trim().length in 4..4
            val pin = binding.pin.text.toString()
            if(pin == savedPin.toString() && check)
                view.findNavController().navigate(R.id.action_GHomeFragment_to_GGraphFragment)
            else if(!check) binding.pin.error="inserire 4 numeri"
            else binding.pin.error="pin non corretto"

        }
        return binding.root
    }
}
