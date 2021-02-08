package it.polito.ic2020.did_kidbalanceapplication.AddChild

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightViewModel
import it.polito.ic2020.did_kidbalanceapplication.database.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.io.File

class homeFragment : Fragment() {
   lateinit var childWeightViewModel: ChildWeightViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_home, container, false)
        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addChild2)
        }

        view.genitore.setOnClickListener {
            val filename = "logIN.txt"
            val file = File(context?.filesDir?.absolutePath, filename)
            val fileExists = file.exists()
            if(fileExists){
                findNavController().navigate(R.id.action_homeFragment_to_GHomeFragment2)
            } else {
                findNavController().navigate(R.id.action_homeFragment_to_logGFragment)
            }
        }

        //Recyclerview
        val adapter= HomeAdapter()
        val recyclerView= view.recyclerView
        recyclerView.adapter= adapter
        recyclerView.layoutManager= GridLayoutManager(requireContext(),3)

        //UserViewModel
        childWeightViewModel = ViewModelProvider(this).get(ChildWeightViewModel::class.java)
        childWeightViewModel.readAllData.observe(viewLifecycleOwner, {
            user-> adapter.setData(user)
        })


    return view
    }


}