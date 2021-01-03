package it.polito.ic2020.did_kidbalanceapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGHomeBinding
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGraphGBinding

class GGraphFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGraphGBinding> (inflater, R.layout.fragment_graph_g,container,false)
        return binding.root
    }
}