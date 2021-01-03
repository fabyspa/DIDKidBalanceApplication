package it.polito.ic2020.did_kidbalanceapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.databinding.DataBindingUtil
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentBHomeBinding


class BHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentBHomeBinding> (inflater, R.layout.fragment_b_home,container,false)
        binding.button.setOnClickListener{
         view: View ->
         view.findNavController().navigate (R.id.action_BHomeFragment_to_gameFragment)
        }
        return binding.root
    }

    //commento di Matte
    //commentodiFabi
    //commentodellamamma 2
    //ci riprovo
}