package it.polito.ic2020.did_kidbalanceapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGHomeBinding

class GHomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGHomeBinding> (inflater, R.layout.fragment_g_home,container,false)
        //binding.button.setOnClickListener{
        //        view: View ->
        //    view.findNavController().navigate (R.id.action_BHomeFragment_to_gameFragment)
        //}
        return binding.root
    }
}
