package it.polito.ic2020.did_kidbalanceapplication.Parent

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightViewModel
import it.polito.ic2020.did_kidbalanceapplication.database.childViewParentAdapter
import kotlinx.android.synthetic.main.fragment_child_list_parent.view.*
import java.io.File


class child_list_parentFragment : Fragment()   {
   lateinit var childWeightViewModel: ChildWeightViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val b = this.arguments
        val firstTime = b?.get("NoChild")
        println("FIRST TIME" + firstTime)

        // Inflate the layout for this fragment
        val GName = File(context?.filesDir?.absolutePath+"/name.txt").readText()
        println(GName)
        val view= inflater.inflate(R.layout.fragment_child_list_parent, container, false)

        if(firstTime == null){
            println("ci sono bambini")
            view.welcome_header.text = """${getString(R.string.welcome_header)} $GName!"""
        } else {
            println("no child")
            view.welcome_header.text = """${getString(R.string.welcome_first_time)} $GName!"""
            view.header_home_g.text = getString(R.string.header_home_g_first)
        }


        view.nuovo_b.setOnClickListener {
            findNavController().navigate(R.id.action_child_list_parentFragment_to_addChild2)
        }
        //Recyclerview
        val adapter= childViewParentAdapter()
        val recyclerView= view.rv_parent
        recyclerView.adapter= adapter
        recyclerView.layoutManager= LinearLayoutManager(requireContext())

        //UserViewModel
        childWeightViewModel = ViewModelProvider(this).get(ChildWeightViewModel::class.java)
        childWeightViewModel.readAllData.observe(viewLifecycleOwner, { user ->
            adapter.setData(user)
        })
        // This callback will only be called when MyFragment is at least Started.
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    findNavController().navigate(R.id.homeFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        // The callback can be enabled or disabled here or in handleOnBackPressed()

    return view
    }


}