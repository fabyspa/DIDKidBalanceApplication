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
import it.polito.ic2020.did_kidbalanceapplication.database.childViewParentAdapter
import kotlinx.android.synthetic.main.fragment_child_list_parent.view.*

class child_list_parentFragment : Fragment() {
   lateinit var childWeightViewModel: ChildWeightViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_child_list_parent, container, false)

        //Recyclerview
        val adapter= childViewParentAdapter()
        val recyclerView= view.rv_parent
        recyclerView.adapter= adapter
        recyclerView.layoutManager= LinearLayoutManager(requireContext())

        //UserViewModel
        childWeightViewModel = ViewModelProvider(this).get(ChildWeightViewModel::class.java)
        childWeightViewModel.readAllData.observe(viewLifecycleOwner, {
            user-> adapter.setData(user)
        })


    return view
    }


}